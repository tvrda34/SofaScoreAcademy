package com.example.sofascoreacademy.project.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sofascoreacademy.databinding.ActivityAboutBinding

class About : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.back.setOnClickListener {
            this.finish()
        }
    }
}