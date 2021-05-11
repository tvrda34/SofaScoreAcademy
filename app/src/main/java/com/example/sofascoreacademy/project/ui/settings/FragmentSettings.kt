package com.example.sofascoreacademy.project.ui.settings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.SettingsBinding
import com.example.sofascoreacademy.project.MainActivity
import com.example.sofascoreacademy.project.helper.LanguageHelper
import com.example.sofascoreacademy.project.ui.About
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel

const val LANGUAGE_EN = "en"
const val LANGUAGE_HR = "hr"

class FragmentSettings : Fragment() {
    private var _binding: SettingsBinding? = null
    private val binding get() = _binding!!
    private var firstSelection = true
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = SettingsBinding.inflate(inflater, container, false)
        sharedViewModel.getFavourites(requireContext())
        sharedViewModel.getLat(requireContext())
        sharedViewModel.getRecent(requireContext())

        val languageArray = requireContext().resources.getStringArray(R.array.available_languages)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, languageArray)
        binding.splang?.setAdapter(adapter)

        val currentLanguageCode = LanguageHelper.getPreferredLanguage(requireContext())
        if (currentLanguageCode == LANGUAGE_HR) {
            binding.splang?.setSelection(1)
        }

        val cityArray = ArrayList<String>()
        sharedViewModel.favourites.observe(viewLifecycleOwner, {
            for (i in it) {
                if (!cityArray.contains(i.title))
                    cityArray.add(i.title)
            }
        })


        binding.splang?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                    ) {
                        if (firstSelection) {
                            firstSelection = false
                        } else {
                            val item = parent.getItemAtPosition(position) as String
                            val tempLanguageCode = languageStringToCode(item)
                            if (tempLanguageCode != currentLanguageCode) {
                                LanguageHelper.setPreferredLanguage(requireContext(), tempLanguageCode)
                                restartApp()
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // nothing happens
                    }
                }

        //TODO ADD CITY CHECK FOR DISTANCE OR ADD MOBILE LOCATION FOR DISTANCE
        /*
        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item, cityArray)
            binding.spcity?.setAdapter(adapter2)

            //poboljsat
            binding.spcity?.setSelection(0)


        binding.spcity?.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                        ) {
                            if (firstSelection) {
                                firstSelection = false
                            } else {
                                val item = parent.getItemAtPosition(position) as String
                                //u bazu upis novog grada i koordinata
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // nothing happens
                        }
                    }*/


        binding.aboutc.more.setOnClickListener {
            requireContext().startActivity(
                    Intent(activity, About::class.java)
            )
        }

        binding.clearfav.setOnClickListener {
            val favAlert = LayoutInflater.from(context).inflate(R.layout.popup_fav, null)
            val alert = AlertDialog.Builder(requireContext()).setView(favAlert).show()

            val btn1 = favAlert.findViewById<Button>(R.id.cancelf)
            val btn2 = favAlert.findViewById<Button>(R.id.clearf)

            btn2.setOnClickListener {
                sharedViewModel.deleteAllFavourite(requireContext())
                alert.dismiss()
            }

            btn1.setOnClickListener {
                alert.dismiss()
            }
        }

        binding.clearrec.setOnClickListener {
            val favAlert = LayoutInflater.from(context).inflate(R.layout.pop_rec, null)
            val alert = AlertDialog.Builder(requireContext()).setView(favAlert).show()

            val btn1 = favAlert.findViewById<Button>(R.id.btn_cancelr)
            val btn2 = favAlert.findViewById<Button>(R.id.btn_clearr)

            btn1.setOnClickListener {
                sharedViewModel.deleteAllRecent(requireContext())
                alert.dismiss()
            }

            btn2.setOnClickListener {
                alert.dismiss()
            }
        }

        return binding.root
    }

    private fun languageStringToCode(languageString: String): String {
        return when (languageString) {
            requireContext().getString(R.string.language_en) -> LANGUAGE_EN
            requireContext().getString(R.string.language_hr) -> LANGUAGE_HR
            else -> ""
        }
    }

    private fun restartApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(activity, MainActivity::class.java)
            val cn = intent.component
            val mainIntent = Intent.makeRestartActivityTask(cn)
            startActivity(mainIntent)
            Process.killProcess(Process.myPid())
        }, 300)
    }
}