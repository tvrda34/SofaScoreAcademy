package com.example.sofascoreacademy.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.DailyTimeBinding
import com.example.sofascoreacademy.project.model.City
import kotlin.math.roundToInt

class WeatherRecyclerAdapter(val context: Context, val locations: List<City>)
    : RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DailyTimeBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.daily_time, parent, false)
        return WeatherViewHolder(view)
    }


    override fun onBindViewHolder(holder: WeatherRecyclerAdapter.WeatherViewHolder, position: Int) {
        val city = locations[position]

        when (city.formattedDay()) {
            0 -> holder.binding.timeDay.text = context.getString(R.string.mon)
            1 -> holder.binding.timeDay.text = context.getString(R.string.tue)
            2 -> holder.binding.timeDay.text = context.getString(R.string.wed)
            3 -> holder.binding.timeDay.text = context.getString(R.string.thu)
            4 -> holder.binding.timeDay.text = context.getString(R.string.fri)
            5 -> holder.binding.timeDay.text = context.getString(R.string.sat)
            else -> {
                holder.binding.timeDay.text = context.getString(R.string.sun)
            }
        }

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
