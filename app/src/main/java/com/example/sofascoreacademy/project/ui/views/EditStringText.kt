package com.example.sofascoreacademy.project.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.EditStringTextBinding

@SuppressLint("InflateParams")
class EditStringText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val binding: EditStringTextBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_string_text, null, false)
        binding = EditStringTextBinding.bind(view)
        addView(binding.root)
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.EditStringText, 0, 0).apply {
            try {
                binding.enterTitle.text = getString(R.styleable.EditStringText_textViewText)
                binding.nameInput.hint = getString(R.styleable.EditStringText_textHint)
            } finally {
                recycle()
            }
        }
    }

    fun getCurrentText(): String {
        return binding.nameInputText.text.toString()
    }

    fun reset() {
        binding.run { nameInputText.text?.clear() }
    }
}