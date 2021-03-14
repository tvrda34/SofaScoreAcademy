package com.example.sofascoreacademy.dz2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.ActivityMainDZ2Binding
import com.example.sofascoreacademy.dz2.fragments.FragmentPoziv
import com.example.sofascoreacademy.dz2.fragments.FragmentRepka

class MainActivityDZ2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainDZ2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDZ2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pozivFrag = FragmentPoziv()
        val repkaFrag = FragmentRepka()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragHol, pozivFrag)
            commit()
        }



        binding.buttonFragP.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragHol, pozivFrag)
                addToBackStack(null)
                commit()
            }
        }
        binding.buttonFragR.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragHol, repkaFrag)
                addToBackStack(null)
                commit()
            }
        }
    }
}