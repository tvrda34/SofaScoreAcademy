package com.example.sofascoreacademy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HelloWorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)

        val button: Button = findViewById(R.id.button)
        val textView: TextView = findViewById(R.id.textView)
        button.setOnClickListener { viewsChange(button, textView) }
    }

    private fun viewsChange(button: Button, textView: TextView) {
        if (textView.text.isEmpty()) {
            textView.text = getString(R.string.textview_show)
            button.text = getString(R.string.buttontext_hide)
        } else {
            textView.text = ""
            button.text = getString(R.string.buttontext_show)
        }
    }
}