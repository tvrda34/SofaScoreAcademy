package com.example.sofascoreacademy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HelloWorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener { viewsChange(button) }
    }

    private fun viewsChange(button: Button) {
        val textview: TextView = findViewById(R.id.textView)
        if (textview.text.isEmpty()) {
            textview.setText(getString(R.string.textview_show))
            button.setText(getString(R.string.buttontext_hide))
        } else {
            textview.setText(null) // ne znam je li bolja opcija ("") ili je svejedno
            button.setText(R.string.buttontext_show)
        }
    }
}