package com.example.sofascoreacademy.project.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.SettingsBinding
import com.example.sofascoreacademy.project.MainActivity

class FragmentSettings : Fragment() {
    private var _binding: SettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsBinding.inflate(inflater, container, false)

        binding.btnEng.setOnClickListener {
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
        }

        return binding.root
    }

}