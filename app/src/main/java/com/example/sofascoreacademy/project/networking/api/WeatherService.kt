package com.example.sofascoreacademy.project.networking.api

import com.example.sofascoreacademy.project.model.City
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("location/search/")
    suspend fun getLocation(@Query("query") location: String): Response<List<Locations>>

    @GET("location/{id}")
    suspend fun getLocationInfo(@Path("id") number: Int): Response<SpecLoc>

    @GET("/api/location/{woeid}/{date}/")
    suspend fun getDailyInfo(
            @Path("woeid") woeid: String,
            @Path("date") date: String
    ): Response<List<City>>

}