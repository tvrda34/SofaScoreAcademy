package com.example.sofascoreacademy.project.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofascoreacademy.project.database.WeatherDatabase
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent
import com.example.sofascoreacademy.project.model.SpecLoc
import com.example.sofascoreacademy.project.networking.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class SharedViewModel : ViewModel() {
    private val cityList = MutableLiveData<Response<List<Locations>>>()
    private val locDetail = MutableLiveData<Response<SpecLoc>>()
    private val favourites = MutableLiveData<List<Locations>>()
    private val recent = MutableLiveData<List<Locations>>()
    private val recData = MutableLiveData<SpecLoc>()

    fun getCity(search: String) {
        viewModelScope.launch {
            val response: Response<List<Locations>> = Repository().getLocations(search)
            cityList.value = response
        }
    }

    fun getLocData(woeid: Int) {
        viewModelScope.launch {
            val response: Response<SpecLoc> = Repository().getSpecLoc(woeid)
            response.body()?.parent?.let { Log.d("coru", it.title) }
            locDetail.value = response
        }
    }

    fun getCityList(): MutableLiveData<Response<List<Locations>>> {
        return cityList
    }

    fun getLocDetails(): MutableLiveData<Response<SpecLoc>> {
        return locDetail
    }

    //favourites
    fun addCityToDb(context: Context, location: Locations) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.addFavouriteCity(location)
            getFavourites(context)
        }
    }

    fun getFavourites(context: Context) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            favourites.value = db?.weatherDao()?.getAll()
        }
    }

    fun getFavouriteList(): MutableLiveData<List<Locations>> {
        return favourites
    }

    //remove city from db
    fun removeCity(context: Context, location: Locations) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.delete(location)
        }
    }


    //recent
    fun addRecentToDb(context: Context, location: Recent) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.addRecent(location)
            getRecent(context)
        }
    }

    fun getRecent(context: Context) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            recent.value = db?.weatherDao()?.getRecent()
        }
    }

    fun getRecentList(): MutableLiveData<List<Locations>> {
        return recent
    }

    //data
    fun getRecyclerInfo(woeid: Int) {
        viewModelScope.launch {
            val response: Response<SpecLoc> = Repository().getSpecLoc(woeid)
            recData.value = response.body()
            Log.d("aaaaaaaa", recData.value.toString())
        }
    }

    fun getRecyclerData(): MutableLiveData<SpecLoc> {
        return recData
    }
}