package com.example.sofascoreacademy.project.networking.repository

import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.networking.api.Network

class Repository {
    suspend fun getLocations(city: String): List<Locations> {
        return Network().getService().getLocation(city)
    }

    suspend fun getSpecLoc(id: Int): SpecLoc {
        return Network().getService().getLocationInfo(id)
    }
}