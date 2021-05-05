package com.example.sofascoreacademy.project.database

import androidx.room.*
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Locations")
    suspend fun getAll(): List<Locations>

    @Query("SELECT * FROM Recent")
    suspend fun getRecent(): List<Locations>

    @Query("SELECT * FROM Locations WHERE woeid IN (:locationIds)")
    suspend fun loadAllByIds(locationIds: IntArray): List<Locations>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg users: Locations)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteCity(location: Locations)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecent(recent: Recent)

    @Delete
    suspend fun delete(location: Locations)
}