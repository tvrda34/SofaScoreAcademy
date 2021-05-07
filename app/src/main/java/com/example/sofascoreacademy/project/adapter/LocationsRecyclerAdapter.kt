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
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.LocationItemBinding
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent
import com.example.sofascoreacademy.project.ui.citydetail.CityDetail
import com.example.sofascoreacademy.project.ui.search.FragmentSearch

class LocationsRecyclerAdapter(val context: Context, val locations: List<Locations>, val frag: FragmentSearch, val fav: ArrayList<Locations>)
    : RecyclerView.Adapter<LocationsRecyclerAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = LocationItemBinding.bind(view)

        init {
            view.setOnClickListener {
                frag.addRecent(
                        Recent(locations[adapterPosition].title, locations[adapterPosition].location_type, locations[adapterPosition].woeid, locations[adapterPosition].latt_long))

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

        holder.binding.title.text = location.title
        holder.binding.dist.text = context.getString(R.string.distance).plus(location.latt_long)

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
    }

    override fun getItemCount(): Int {
        return locations.size
    }


}
