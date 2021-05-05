package com.example.sofascoreacademy.project.networking.repository

import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.model.favouriteCity
import com.example.sofascoreacademy.project.networking.api.Network
import retrofit2.Response

class Repository {
    suspend fun getLocations(city: String): Response<List<Locations>> {
        return Network().getService().getLocation(city)
    }

    suspend fun getSpecLoc(id: Int): Response<SpecLoc> {
        return Network().getService().getLocationInfo(id)
    }

    suspend fun getFavDetail(id: Int): Response<favouriteCity> {
        return Network().getService().getFavDetail(id)
    }
}