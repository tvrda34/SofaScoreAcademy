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
import coil.clear
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.ReorderFavBinding
import com.example.sofascoreacademy.project.helper.ImageHelper
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

    var reorderSwitch: Boolean = false

    inner class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ReorderFavBinding.bind(view)

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
        val view = LayoutInflater.from(context).inflate(R.layout.reorder_fav, parent, false)
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
        holder.binding.temp.text =
            data.body()?.consolidated_weather?.get(0)?.the_temp?.roundToInt().toString().plus("°")

        holder.binding.weatherPic.setBackgroundColor(0)

        val imgHelper = data.body()?.consolidated_weather?.get(0)?.weather_state_name?.let {
            ImageHelper(
                it
            )
        }
        val imgRes = imgHelper?.getImgResource()
        if (imgRes != null) {
            holder.binding.weatherPic.load(imgRes)
        }


        if (reorderSwitch) {
            holder.binding.move.load(R.drawable.ic_reorder)
        } else {
            holder.binding.move.clear()
        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }


}



