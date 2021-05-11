package com.example.sofascoreacademy.project.ui.search


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofascoreacademy.databinding.FragmentSearchBinding
import com.example.sofascoreacademy.project.adapter.LocationsRecyclerAdapter
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel
import retrofit2.Response


class FragmentSearch : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        FragmentSearchBinding.inflate(inflater, container, false).also { _binding = it }
        sharedViewModel.getFavourites(requireContext())
        sharedViewModel.getLat(requireContext())
        sharedViewModel.getRecent(requireContext())

        val list = ArrayList<Locations>()
        sharedViewModel.favourites.observe(viewLifecycleOwner, { favs ->
            list.addAll(favs)
        })

        val recDet = ArrayList<Response<SpecLoc>>()
        sharedViewModel.recDetails.observe(viewLifecycleOwner, { rec ->
            recDet.addAll(rec)
        })

        val cityCoord = ArrayList<String>()
        sharedViewModel.lat.observe(viewLifecycleOwner, {
            cityCoord.addAll(it.split(","))
        })

        binding.citySearchEditText.threshold = 3
        binding.citySearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.citySearchEditText.isCursorVisible = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.citySearchEditText.isCursorVisible = true
                val query = s.toString()
                if (s?.length!! > 2) {
                    sharedViewModel.getCity(query)

                }
            }

            override fun afterTextChanged(s: Editable?) {
                binding.citySearchEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
            }
        })

        //layout promjena izgleda
        val constraintLayout: ConstraintLayout = binding.slay
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        binding.recView.visibility = View.INVISIBLE
        constraintSet.connect(binding.textView.id, ConstraintSet.TOP, binding.toolbar.id, ConstraintSet.BOTTOM, 0)
        constraintSet.applyTo(constraintLayout)

        sharedViewModel.cityList.observe(viewLifecycleOwner, { locations ->
            if (locations.isSuccessful && locations.body()?.isNotEmpty() == true) {
                //promjena izgleda layouta
                binding.recView.visibility = View.VISIBLE
                constraintSet.connect(
                    binding.textView.id,
                    ConstraintSet.TOP,
                    binding.recView.id,
                    ConstraintSet.BOTTOM,
                    0
                )
                constraintSet.applyTo(constraintLayout)
                sharedViewModel.searchDetails.observe(viewLifecycleOwner, { details ->
                    val adapter = locations.body()?.let {
                        LocationsRecyclerAdapter(
                            requireContext(),
                            it,
                            this,
                            list,
                            cityCoord,
                            details
                        )
                    }
                    binding.recView.adapter = adapter
                    binding.recView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recView.setHasFixedSize(true)
                })
            }
        })

        sharedViewModel.recent.observe(viewLifecycleOwner, { locations ->
            if (locations.isNotEmpty()) {
                sharedViewModel.favourites.observe(viewLifecycleOwner, { favs ->
                    val adapter = locations?.let {
                        LocationsRecyclerAdapter(
                            requireContext(),
                            it,
                            this,
                            favs as ArrayList<Locations>,
                            cityCoord,
                            recDet
                        )
                    }
                    binding.recViewRecent.adapter = adapter
                    binding.recViewRecent.layoutManager = LinearLayoutManager(requireContext())
                    binding.recViewRecent.setHasFixedSize(true)
                })
            }
        })


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.d("ResponseFragSearch", binding.citySearchEditText.text.toString())
            sharedViewModel.getCity(binding.citySearchEditText.text.toString())
            binding.citySearchEditText.setText("")
            binding.citySearchEditText.clearFocus()
            // Hide the keyboard
            val inputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    fun addToFavourites(location: Locations) {
        sharedViewModel.addCityToDb(requireContext(), location)
    }

    fun removeFromFavourites(location: Locations) {
        sharedViewModel.removeCity(requireContext(), location)
    }

    fun addRecent(location: Recent) {
        sharedViewModel.addRecentToDb(requireContext(), location)
    }


}



