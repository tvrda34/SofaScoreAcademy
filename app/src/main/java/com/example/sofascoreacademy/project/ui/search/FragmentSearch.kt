package com.example.sofascoreacademy.project.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofascoreacademy.databinding.FragmentSearchBinding
import com.example.sofascoreacademy.project.adapter.LocationsRecyclerAdapter
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel


class FragmentSearch : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        FragmentSearchBinding.inflate(inflater, container, false).also { _binding = it }

        sharedViewModel.getCityList().observe(viewLifecycleOwner, { locations ->
            val adapter = LocationsRecyclerAdapter(requireContext(), locations)
            binding.recView.adapter = adapter
            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.setHasFixedSize(true)
            if (locations != null) {
                askDetails(locations[0].woeid)
                Log.d("resp", locations[0].title)
            }
        })

        return binding.root
    }

    private fun askDetails(id: Int) {
        sharedViewModel.getLocData(id)
    }

    /* private fun actCall(locDetail: SpecLoc) {
         val intent = Intent(activity, CityDetail::class.java).putExtra("extra", locDetail)
         startActivity(intent)
     }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /*pokusao sam nadjacat glavni toolbar ali nije mi islo

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView((requireContext() as MainActivity).supportActionBar!!.themedContext)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.setActionView(searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                Log.d("Fraq", query)
                searchView.setQuery(searchView.getQuery(), true);
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {}
        }
        )
    }*/

}


