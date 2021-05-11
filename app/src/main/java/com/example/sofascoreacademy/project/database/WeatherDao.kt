package com.example.sofascoreacademy.project.database

import androidx.room.*
import com.example.sofascoreacademy.project.model.BaseCity
import com.example.sofascoreacademy.project.model.LocationDb
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Locations")
    suspend fun getAll(): List<Locations>

    @Query("SELECT * FROM Recent")
    suspend fun getRecent(): List<Locations>

    @Query("SELECT COUNT(*) FROM Locations")
    suspend fun locationCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg users: Locations)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteCity(location: Locations)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecent(recent: Recent)

    @Delete
    suspend fun delete(location: Locations)

    @Query("DELETE FROM Locations")
    suspend fun deleteAllFromTableLocations()

    @Query("DELETE FROM Recent")
    suspend fun deleteAllFromTableRecent()

    @Query("SELECT latt_long FROM BaseCity")
    suspend fun getBaseCity(): String

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBaseCity(base: BaseCity)

    //nova baza - s redoslijedom

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteCity2(location: LocationDb)

    @Query("SELECT COUNT(*) FROM LocationDb")
    suspend fun locationCount2(): Int

    @Query("SELECT * FROM LocationDb")
    suspend fun getAll2(): List<LocationDb>

    @Query("UPDATE LocationDb SET position = :newpos WHERE position = :pos")
    suspend fun updatePos(newpos: Int, pos: Int)

}