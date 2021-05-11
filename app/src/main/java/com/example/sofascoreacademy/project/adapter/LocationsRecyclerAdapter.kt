package com.example.sofascoreacademy.project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.LocationItemBinding
import com.example.sofascoreacademy.project.helper.ImageHelper
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.ui.citydetail.CityDetail
import com.example.sofascoreacademy.project.ui.search.FragmentSearch
import retrofit2.Response
import kotlin.math.*

class LocationsRecyclerAdapter(
    val context: Context,
    val locations: List<Locations>,
    val frag: FragmentSearch,
    val fav: ArrayList<Locations>,
    val dist: List<String>,
    val det: List<Response<SpecLoc>>
) : RecyclerView.Adapter<LocationsRecyclerAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = LocationItemBinding.bind(view)

        init {
            view.setOnClickListener {
                frag.addRecent(
                    Recent(
                        locations[adapterPosition].title,
                        locations[adapterPosition].location_type,
                        locations[adapterPosition].woeid,
                        locations[adapterPosition].latt_long
                    )
                )

                it.context.startActivity(
                        Intent(
                                it.context,
                                CityDetail::class.java
                        ).putExtra("extra", locations[adapterPosition])
                )
            }
        }
    }
    //lateinit var myfrag: FragmentSearch

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        val data = det[position]

        holder.binding.title.text = location.title
        holder.binding.coord.text = location.latt_long
        if (dist.isNotEmpty()) {
            holder.binding.dist.text = context.getString(R.string.distance).plus(distanceCalculate(location.latt_long))
        }

        if (fav.contains(location)) {
            holder.binding.star.tag = "starnov"
            holder.binding.star.setBackgroundResource(R.drawable.ic_star_1)
        } else {
            holder.binding.star.tag = "starpoc"
            holder.binding.star.setBackgroundResource(R.drawable.ic_star_0)
        }

        holder.binding.star.setOnClickListener {
            if (holder.binding.star.tag.equals("starpoc")) {
                Log.d("TrecView_STAR_CLICK", "0>1")
                it.setBackgroundResource(R.drawable.ic_star_1)
                holder.binding.star.tag = "starnov"
                //myfrag.addToFavourites(location)
                frag.addToFavourites(location)
            } else {
                Log.d("TrecView_STAR_CLICK", "1>0")
                it.setBackgroundResource(R.drawable.ic_star_0)
                holder.binding.star.tag = "starpoc"
                frag.removeFromFavourites(location)
            }
        }

        val imgHelper = data.body()?.consolidated_weather?.get(0)?.weather_state_name?.let {
            ImageHelper(
                it
            )
        }
        val imgRes = imgHelper?.getImgResource()
        if (imgRes != null) {
            holder.binding.weatherPic.load(imgRes)
        }
        holder.binding.weatherPic.setBackgroundColor(0)
        holder.binding.temp.text =
            data.body()?.consolidated_weather?.get(0)?.the_temp?.roundToInt().toString().plus("°")
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    private fun distanceCalculate(latt: String): String {
        val coord = latt.split(",")

        val lon1 = Math.toRadians(coord[1].toDouble())
        val lon2 = Math.toRadians(dist[1].toDouble())
        val lat1 = Math.toRadians(coord[0].toDouble())
        val lat2 = Math.toRadians(dist[1].toDouble())

        // Haversine formula
        val dlon: Double = lon2 - lon1
        Log.d("Dist-Lon1", Math.toRadians(coord[1].toDouble()).toString())
        Log.d("Dist-Lon2", Math.toRadians(dist[1].toDouble()).toString())
        Log.d("Dist-Latt1", Math.toRadians(coord[0].toDouble()).toString())
        Log.d("Dist-Latt2", Math.toRadians(dist[0].toDouble()).toString())
        val dlat: Double = lat2 - lat1
        if (dlat != 0.0 && !(dlon == 0.0)) {
            val a = (sin(dlat / 2).pow(2.0)
                    + (cos(lat1) * cos(lat2)
                    * sin(dlon / 2).pow(2.0)))

            val c = 2 * asin(sqrt(a))

            // Radius of earth in kilometers. Use 3956 for miles
            val r = 6371.0


            // calculate the result
            return ((c * r) - 3064).roundToInt().toString().plus(" km")
        } else {
            return "0 km"
        }
    }


}
