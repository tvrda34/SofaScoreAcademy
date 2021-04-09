package com.example.sofascoreacademy.project.networking.api

import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("location/search/")
    suspend fun getLocation(@Query("query") location: String): List<Locations>

    @GET("location/{id}")
    suspend fun getLocationInfo(@Path("id") number: Int): SpecLoc

}