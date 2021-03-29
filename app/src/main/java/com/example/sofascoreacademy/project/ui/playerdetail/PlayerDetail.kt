package com.example.sofascoreacademy.project.ui.playerdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.ActivityPlayerDetailBinding
import com.example.sofascoreacademy.project.model.Footballer
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class PlayerDetail : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        val view = binding.root
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Snackbar.make(view, getString(R.string.player_details_info), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val player = intent.getSerializableExtra("extra") as Footballer
        binding.playerDetail.itemImage.load(player.image) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.hns)
        }
        binding.toolbarLayout.title = player.name.plus(" ").plus(player.surname)
        binding.playerDetail.itemClub.text =
            getString(R.string.textClub).plus(": ").plus(player.club)
        binding.playerDetail.itemPos.text =
            getString(R.string.textPosition).plus(": ").plus(player.position)
        binding.playerDetail.itemApper.text =
            getString(R.string.textApper).plus(": ").plus(player.apperanceNum.toString())
        binding.playerDetail.itemStatus.text =
            getString(R.string.statusText).plus(": ").plus(player.teamRole.toString())
    }
}