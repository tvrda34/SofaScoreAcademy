package com.example.sofascoreacademy.project.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.MycityFavBinding
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.ui.citydetail.CityDetail
import com.example.sofascoreacademy.project.ui.mycity.FragmentMyCity
import retrofit2.Response
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.roundToInt


class FavouriteRecyclerAdapter(val context: Context, val locations: ArrayList<Locations>, private val frag: FragmentMyCity, private val detail: List<Response<SpecLoc>>) :
        RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MycityFavBinding.bind(view)

        init {
            view.setOnClickListener {
                it.context.startActivity(
                        Intent(
                                it.context,
                                CityDetail::class.java
                        ).putExtra("extra", locations[adapterPosition])
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mycity_fav, parent, false)
        return FavouriteViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
            holder: FavouriteRecyclerAdapter.FavouriteViewHolder,
            position: Int
    ) {
        val location = locations[position]
        val data = detail[position]

        holder.binding.title.text = location.title
        holder.binding.star.setOnClickListener {
            frag.removeCity(location)
            locations.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(context, location.title.plus(context.getString(R.string.remove_msg)), Toast.LENGTH_SHORT).show()
        }


        val zone = ZoneId.of(data.body()?.timezone)
        val zonedDateTime: ZonedDateTime = ZonedDateTime.now(zone)
        val offset: ZoneOffset = zonedDateTime.getOffset()
        Log.d("zonaID", offset.toString())

        holder.binding.time.text = data.body()?.formattedTime()
        holder.binding.tzone.text = context.getString(R.string.gmt).plus(offset.toString())
        holder.binding.temp.text = data.body()?.consolidated_weather?.get(0)?.the_temp?.roundToInt().toString()

        holder.binding.weatherPic.setBackgroundColor(0)

        when (data.body()?.consolidated_weather?.get(0)?.weather_state_name) {
            "Clear" -> holder.binding.weatherPic.load(R.drawable.ic_c)
            "Heavy Cloud" -> holder.binding.weatherPic.load(R.drawable.ic_hc)
            "Sleet" -> holder.binding.weatherPic.load(R.drawable.ic_sl)
            "Snow" -> holder.binding.weatherPic.load(R.drawable.ic_sn)
            "Light Rain" -> holder.binding.weatherPic.load(R.drawable.ic_lr)
            "Heavy Rain" -> holder.binding.weatherPic.load(R.drawable.ic_hr)
            "Thunderstorm" -> holder.binding.weatherPic.load(R.drawable.ic_t)
            "Showers" -> holder.binding.weatherPic.load(R.drawable.ic_s)
            else -> { // Note the block
                holder.binding.weatherPic.load(R.drawable.ic_lc)
            }
        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }


}



