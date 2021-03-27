package com.example.sofascoreacademy.project.ui.call

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.FragmentPozivBinding
import com.example.sofascoreacademy.project.model.Footballer
import com.example.sofascoreacademy.project.model.TeamRole
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel

class FragmentPoziv : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentPozivBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        FragmentPozivBinding.inflate(inflater, container, false).also { _binding = it }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, TeamRole.values())
        binding.positionSpinner.adapter = adapter
        binding.buttonAdd.setOnClickListener { addPlayer() }
        return binding.root
    }

    private fun addPlayer() {
        if (binding.nameInput.getCurrentText().isEmpty() || binding.surnameInput.getCurrentText()
                .isEmpty()
            || binding.ageInputText.text.isNullOrEmpty() || binding.positionSpinner.selectedItem == null
            || binding.clubInput.getCurrentText()
                .isEmpty() || binding.posRadio.radioGroup.checkedRadioButtonId == -1
            || binding.imageInput.getCurrentText().isEmpty()
        ) {
            binding.invalidEnter.setText(R.string.kriviUnos)
            binding.invalidEnter.setTextColor(Color.RED)
            binding.invalidEnter.postDelayed({ binding.invalidEnter.text = "" }, 1000)
            return
        }

        val footballer = Footballer(
            binding.nameInput.getCurrentText(),
            binding.surnameInput.getCurrentText(),
            binding.ageInputText.text.toString().toInt(),
            when (binding.posRadio.radioGroup.checkedRadioButtonId) {
                R.id.optionGoalkeeper -> getString(R.string.golman)
                R.id.optionDefender -> getString(R.string.branic)
                R.id.optionMidfielder -> getString(R.string.vezni)
                else -> getString(R.string.napadac)
            },
            binding.clubInput.getCurrentText(),
            binding.positionSpinner.selectedItem as TeamRole,
            binding.apperInputText.text.toString().toInt(),
            binding.imageInput.getCurrentText()
        )
        sharedViewModel.addToList(footballer)

        Toast.makeText(requireContext(), getString(R.string.call_succ), Toast.LENGTH_SHORT).show()

        binding.nameInput.reset()
        binding.surnameInput.reset()
        binding.ageInputText.setText("")
        binding.posRadio.radioGroup.clearCheck()
        binding.clubInput.reset()
        binding.apperInputText.setText("")
        binding.imageInput.reset()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


