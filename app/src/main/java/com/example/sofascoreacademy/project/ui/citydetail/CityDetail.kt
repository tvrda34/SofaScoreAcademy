package com.example.sofascoreacademy.project.ui.citydetail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.CityNameBinding
import com.example.sofascoreacademy.project.adapter.WeatherDailyRecyclerAdapter
import com.example.sofascoreacademy.project.adapter.WeatherRecyclerAdapter
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.viewmodels.SharedViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.roundToInt

const val kmMlConverter = 1.609344


@Suppress("DEPRECATION")
class CityDetail : AppCompatActivity() {
    private lateinit var binding: CityNameBinding
    private val viewModel: SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CityNameBinding.inflate(layoutInflater)
        val view = binding.root
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        viewModel.getFavourites(this)

        val search = intent.getSerializableExtra("extra") as Locations
        val lista = ArrayList<Locations>()
        viewModel.getFavouriteList().observe(this, {
            Log.d("ListaCont", it.size.toString())
            lista.addAll(it)
        })

        viewModel.getLocData(search.woeid)
        viewModel.getLocDetails().observe(this, { response ->
            val city = response.body()
            if (city != null) {

                viewModel.getDaily(search.woeid.toString(), city.consolidated_weather[0].applicable_date.replace("-", "/"))

                binding.weatherDetail.masterw.weatherAccuracy.iconName.text = getString(R.string.accuracy)
                binding.weatherDetail.masterw.weatherHumidity.iconName.text = getString(R.string.humidity)
                binding.weatherDetail.masterw.weatherMinmax.iconName.text = getString(R.string.min_max)
                binding.weatherDetail.masterw.weatherPreasure.iconName.text = getString(R.string.preassure)
                binding.weatherDetail.masterw.weatherVisibility.iconName.text = getString(R.string.visibility)

                //dobivene vrijednosti - base city info
                binding.toolbarLayout.title = city.title
                binding.weatherDetail.masterw.baseInfo.time.text = city.formattedTime().plus(" (" + city.timezone_name + ")")
                binding.weatherDetail.masterw.baseInfo.date.text = city.consolidated_weather[0].formattedApplicableDate()
                binding.weatherDetail.masterw.baseInfo.basicInfo.text = city.consolidated_weather[0].weather_state_name
                binding.weatherDetail.masterw.baseInfo.itemPos.text = city.consolidated_weather[0].the_temp.roundToInt().toString().plus("°")

                //vrijednosti
                binding.weatherDetail.masterw.weatherMinmax.data.text = city.consolidated_weather[0].min_temp.roundToInt().toString().plus("°")
                        .plus("/").plus(city.consolidated_weather[0].max_temp.roundToInt().toString()).plus("°")
                binding.weatherDetail.masterw.weatherWind.data.text = (city.consolidated_weather[0].wind_speed * kmMlConverter).roundToInt().toString().plus("km/h")
                        .plus(" (" + city.consolidated_weather[0].wind_direction_compass + ")")
                binding.weatherDetail.masterw.weatherHumidity.data.text = city.consolidated_weather[0].humidity.toString().plus("%")
                binding.weatherDetail.masterw.weatherPreasure.data.text = city.consolidated_weather[0].air_pressure.roundToInt().toString().plus(" hPa")
                binding.weatherDetail.masterw.weatherVisibility.data.text = (city.consolidated_weather[0].visibility * kmMlConverter).roundToInt().toString().plus(" km")
                binding.weatherDetail.masterw.weatherAccuracy.data.text = city.consolidated_weather[0].predictability.toString().plus("%")


                //slike
                binding.weatherDetail.masterw.baseInfo.weatherPic.setBackgroundColor(0)
                when (city.consolidated_weather[0].weather_state_name) {
                    "Clear" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_c)
                    "Heavy Cloud" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_hc)
                    "Sleet" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_sl)
                    "Snow" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_sn)
                    "Light Rain" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_lr)
                    "Heavy Rain" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_hr)
                    "Thunderstorm" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_t)
                    "Showers" -> binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_s)
                    else -> { // Note the block
                        binding.weatherDetail.masterw.baseInfo.weatherPic.load(R.drawable.ic_lc)
                    }
                }

                //zvijezda
                Log.d("ListaCont", lista.contains(search).toString())
                Log.d("ListaCont", lista.size.toString())
                if (lista.contains(search)) {
                    binding.fav.tag = "starnov"
                    binding.fav.setBackgroundResource(R.drawable.ic_star_1)
                } else {
                    binding.fav.tag = "starpoc"
                    binding.fav.setBackgroundResource(R.drawable.ic_star_0)
                }
                binding.fav.setOnClickListener {
                    if (binding.fav.tag.equals("starpoc")) {
                        it.setBackgroundResource(R.drawable.ic_star_1)
                        binding.fav.tag = "starnov"
                        viewModel.addCityToDb(this, search)
                    } else {
                        it.setBackgroundResource(R.drawable.ic_star_0)
                        binding.fav.tag = "starpoc"
                        viewModel.removeCity(this, search)
                    }
                }

                //recycleri
                val adapter = WeatherRecyclerAdapter(this, city.consolidated_weather)
                binding.weatherDetail.dailyns.recView.adapter = adapter
                binding.weatherDetail.dailyns.recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.weatherDetail.dailyns.recView.setHasFixedSize(true)

                viewModel.getDailyValue().observe(this, {
                    val adapterDaily = it.body()?.let { it1 -> WeatherDailyRecyclerAdapter(this, it1.sortedBy { it.created }) }
                    binding.weatherDetail.dailyw.recView.adapter = adapterDaily
                    binding.weatherDetail.dailyw.recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.weatherDetail.dailyw.recView.setHasFixedSize(true)
                })
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.weatherDetail.masterw.weatherMinmax.icon.background = baseContext.getDrawable(R.drawable.ic_thermostat)
            binding.weatherDetail.masterw.weatherWind.icon.background = baseContext.getDrawable(R.drawable.ic_wind)
            binding.weatherDetail.masterw.weatherHumidity.icon.background = baseContext.getDrawable(R.drawable.ic_humidity)
            binding.weatherDetail.masterw.weatherPreasure.icon.background = baseContext.getDrawable(R.drawable.ic_pressure)
            binding.weatherDetail.masterw.weatherVisibility.icon.background = baseContext.getDrawable(R.drawable.ic_visibility)
            binding.weatherDetail.masterw.weatherAccuracy.icon.background = baseContext.getDrawable(R.drawable.ic_accuracy)
        }

        binding.weatherDetail.dailyns.innerTitle.text = getString(R.string.next_seven)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.weatherDetail.dailyns.innerTitle.setTextAppearance(R.style.AssistiveColdGrayLeft)
        }

        //back u activity
        val bv = findViewById<View>(R.id.back)
        bv.setOnClickListener {
            this.finish()
        }
    }

}