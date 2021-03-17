package com.example.sofascoreacademy.project.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.FragmentPozivBinding
import com.example.sofascoreacademy.project.data.Footballer
import com.example.sofascoreacademy.project.data.SharedViewModel

class FragmentPoziv : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentPozivBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentPozivBinding.inflate(inflater, container, false).also { _binding = it }
        binding.buttonAdd.setOnClickListener { addPlayer() }
        return binding.root
    }

    private fun addPlayer() {
        if (binding.nameInputText.text.isNullOrEmpty() || binding.surnameInputText.text.isNullOrEmpty()
            || binding.ageInputText.text.isNullOrEmpty() || binding.positionInputText.text.isNullOrEmpty()
            || binding.clubInputText.text.isNullOrEmpty()
        ) {
            binding.invalidEnter.setText(R.string.kriviUnos)
            binding.invalidEnter.setTextColor(Color.RED)
            return
        }

        val footballer = Footballer(
            binding.nameInputText.text.toString(),
            binding.surnameInputText.text.toString(),
            binding.ageInputText.text.toString().toInt(),
            binding.positionInputText.text.toString(),
            binding.clubInputText.text.toString()
        )
        binding.invalidEnter.setText(R.string.ispravanUnos)
        binding.invalidEnter.setTextColor(Color.GREEN)

        sharedViewModel.addToList(footballer)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}