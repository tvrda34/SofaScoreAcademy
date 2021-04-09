package com.example.sofascoreacademy.project.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.LocationItemBinding
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.ui.citydetail.CityDetail

class LocationsRecyclerAdapter(val context: Context, val locations: List<Locations>)
    : RecyclerView.Adapter<LocationsRecyclerAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = LocationItemBinding.bind(view)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.binding.itemName.text = location.title
    }

    override fun getItemCount(): Int {
        return locations.size
    }


}
