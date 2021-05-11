package com.example.sofascoreacademy.project.helper

import com.example.sofascoreacademy.R

class ImageHelper(val name: String) {
    fun getImgResource(): Int {
        return when (name) {
            "Clear" -> R.drawable.ic_c
            "Heavy Cloud" -> R.drawable.ic_hc
            "Sleet" -> R.drawable.ic_sl
            "Snow" -> R.drawable.ic_sn
            "Light Rain" -> R.drawable.ic_lr
            "Heavy Rain" -> R.drawable.ic_hr
            "Thunderstorm" -> R.drawable.ic_t
            "Showers" -> R.drawable.ic_s
            else -> { // Note the block
                R.drawable.ic_lc
            }
        }
    }
}