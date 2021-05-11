package com.example.sofascoreacademy.project.ui.mycity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.FragmentFavouriteBinding
import com.example.sofascoreacademy.project.adapter.FavouriteRecyclerAdapter
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel
import retrofit2.Response

class FragmentMyCity : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        sharedViewModel.getFavourites(requireContext())

        val details = ArrayList<Response<SpecLoc>>()
        var adapter: FavouriteRecyclerAdapter? = null
        var img = 0

        sharedViewModel.favourites.observe(viewLifecycleOwner, { fav ->
            if (fav.isNotEmpty()) {
                sharedViewModel.detail.observe(viewLifecycleOwner, {
                    details.addAll(it)
                    binding.msg?.visibility = View.INVISIBLE
                    adapter = FavouriteRecyclerAdapter(requireContext(),
                            fav as ArrayList<Locations>, this, details)
                    binding.recView.adapter = adapter
                    binding.recView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recView.setHasFixedSize(true)
                })
            } else {
                binding.msg?.visibility = View.VISIBLE
                binding.recView.visibility = View.INVISIBLE
            }
        })

        binding.edit.setOnClickListener {
            if (adapter != null) {
                if (img == 0) {
                    binding.edit.setBackgroundResource(R.drawable.ic_done)
                    img = 1
                } else {
                    binding.edit.setBackgroundResource(R.drawable.ic_edit)
                    img = 0
                }
                val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
                itemTouchHelper.attachToRecyclerView(binding.recView)
                adapter!!.reorderSwitch = !adapter!!.reorderSwitch
                adapter!!.notifyDataSetChanged()
            }
        }

        return binding.root
    }


    fun removeCity(locations: Locations) {
        sharedViewModel.removeCity(requireContext(), locations)
    }


    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
        ): Int {
            // Specify the directions of movement
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ): Boolean {
            // Notify your adapter that an item is moved from x position to y position
            binding.recView.adapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            // Called by the ItemTouchHelper when the user interaction with an element is over and it also completed its animation
            // This is a good place to send update to your backend about changes
            // Your API call
        }

    }

}