package com.example.sofascoreacademy.project.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.DailyTimeBinding
import com.example.sofascoreacademy.project.model.City
import kotlin.math.roundToInt

class WeatherDailyRecyclerAdapter(val context: Context, val locations: List<City>)
    : RecyclerView.Adapter<WeatherDailyRecyclerAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DailyTimeBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.daily_time, parent, false)
        return WeatherViewHolder(view)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherDailyRecyclerAdapter.WeatherViewHolder, position: Int) {
        val city = locations[position]

        val time = city.created.subSequence(11, 16) as String

        holder.binding.timeDay.text = time
        holder.binding.temp.text = city.the_temp.roundToInt().toString().plus("°")
        holder.binding.weaImg.setBackgroundColor(0)

        when (city.weather_state_name) {
            "Clear" -> holder.binding.weaImg.load(R.drawable.ic_c)
            "Heavy Cloud" -> holder.binding.weaImg.load(R.drawable.ic_hc)
            "Sleet" -> holder.binding.weaImg.load(R.drawable.ic_sl)
            "Snow" -> holder.binding.weaImg.load(R.drawable.ic_sn)
            "Light Rain" -> holder.binding.weaImg.load(R.drawable.ic_lr)
            "Heavy Rain" -> holder.binding.weaImg.load(R.drawable.ic_hr)
            "Thunderstorm" -> holder.binding.weaImg.load(R.drawable.ic_t)
            "Showers" -> holder.binding.weaImg.load(R.drawable.ic_s)
            else -> {
                holder.binding.weaImg.load(R.drawable.ic_lc)
            }
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }


}
