package com.example.sofascoreacademy.project

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidakademija.helpers.LanguageHelper
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.project.database.WeatherDatabase
import com.example.sofascoreacademy.project.model.BaseCity
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_poziv, R.id.navigation_repka
                )
        )
        WeatherDatabase.getDatabase(this)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        /*if (!isInternetAvailable(this)) {
              AlertDialog.Builder(this).setTitle("No Internet Connection")
                      .setMessage("Please check your internet connection and try again")
                      .setPositiveButton(android.R.string.ok) { _, _ -> }
                      .setIcon(android.R.drawable.ic_dialog_alert).show()*//*
        }*/

        viewModel.getFavourites(this)
        viewModel.getLat(this)
        viewModel.addBaseToDb(this, BaseCity("Zagreb", "45.807259,15.967600"))
    }


    fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        //baseContext?.resources?.updateConfiguration(config, baseContext!!.resources.displayMetrics)
        resources.updateConfiguration(config, resources.displayMetrics)
        val tbar = findViewById<BottomNavigationView>(R.id.nav_view)
        tbar.menu.clear()
        tbar.inflateMenu(R.menu.bottom_nav_menu)
        tbar.selectedItemId = R.id.navigation_settings
    }


    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return result
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapLanguage(newBase))
    }

}