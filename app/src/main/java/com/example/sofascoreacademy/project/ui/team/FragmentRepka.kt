package com.example.sofascoreacademy.project.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofascoreacademy.databinding.FragmentRepkaBinding
import com.example.sofascoreacademy.project.adapter.PlayerRecyclerAdapter
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel

class FragmentRepka : Fragment() {
    private var _binding: FragmentRepkaBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepkaBinding.inflate(inflater, container, false)
        sharedViewModel.getList().observe(viewLifecycleOwner, { players ->
            val adapter = PlayerRecyclerAdapter(requireContext(), players)
            binding.recView.adapter = adapter
            binding.recView.layoutManager = LinearLayoutManager(requireContext())
            binding.recView.setHasFixedSize(true)
        })
        return binding.root

    }
}