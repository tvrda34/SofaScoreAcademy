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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.androidakademija.helpers.LanguageHelper
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.SettingsBinding
import com.example.sofascoreacademy.project.MainActivity
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

        val languageArray = requireContext().resources.getStringArray(R.array.available_languages)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, languageArray)
        binding.lsel?.setAdapter(adapter)

        val currentLanguageCode = LanguageHelper.getPreferredLanguage(requireContext())
        /*if (currentLanguageCode == LANGUAGE_HR) {
        }*/

        val cityArray = ArrayList<String>()
        sharedViewModel.favourites.observe(viewLifecycleOwner, {
            for (i in it) {
                if (!cityArray.contains(i.title))
                    cityArray.add(i.title)
            }
        })

        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item, cityArray)
        binding.csel?.setAdapter(adapter2)

        binding.csel?.onItemSelectedListener =
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


        /*binding.btnEng.setOnClickListener {
            (activity as MainActivity).setLocale("en")
            Toast.makeText(requireContext(), getString(R.string.lang_sel), Toast.LENGTH_SHORT)
                .show()
            binding.titleLang.text = getString(R.string.lang_title)
            binding.btnCro.text = getString(R.string.cro)
            binding.btnEng.text = getString(R.string.eng)
        }

        binding.btnCro.setOnClickListener {
            (activity as MainActivity).setLocale("hr")
            Toast.makeText(requireContext(), getString(R.string.lang_sel), Toast.LENGTH_SHORT)
                .show()
            binding.titleLang.text = getString(R.string.lang_title)
            binding.btnCro.text = getString(R.string.cro)
            binding.btnEng.text = getString(R.string.eng)
        }*/

        binding.aboutc.more.setOnClickListener {
            requireContext().startActivity(
                    Intent(activity, About::class.java)
            )
        }

        binding.clearfav.setOnClickListener {
            sharedViewModel.deleteAllFavourite(requireContext())

        }

        binding.clearrec.setOnClickListener {
            sharedViewModel.deleteAllRecent(requireContext())
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