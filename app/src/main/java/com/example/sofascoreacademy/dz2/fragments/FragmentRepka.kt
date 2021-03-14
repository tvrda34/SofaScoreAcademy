package com.example.sofascoreacademy.dz2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sofascoreacademy.databinding.FragmentRepkaBinding
import com.example.sofascoreacademy.dz2.data.SharedViewModel

class FragmentRepka : Fragment() {
    private var _binding: FragmentRepkaBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepkaBinding.inflate(inflater, container, false)
        sharedViewModel.getList().observe(viewLifecycleOwner, { users ->
            binding.textView2.text = users.joinToString(separator = "\n")
        })
        return binding.root

    }
}