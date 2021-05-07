package com.example.sofascoreacademy.project.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofascoreacademy.project.database.WeatherDatabase
import com.example.sofascoreacademy.project.model.*
import com.example.sofascoreacademy.project.networking.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response


class SharedViewModel : ViewModel() {
    val cityList = MutableLiveData<Response<List<Locations>>>()
    val locDetail = MutableLiveData<Response<SpecLoc>>()
    val favourites = MutableLiveData<List<Locations>>()
    val recent = MutableLiveData<List<Locations>>()
    val daily = MutableLiveData<Response<List<City>>>()
    val detail = MutableLiveData<List<Response<SpecLoc>>>()
    val lat = MutableLiveData<String>()

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
            val response = db?.weatherDao()?.getAll()
            val detailResponse = response?.map { detail -> async { Repository().getSpecLoc(detail.woeid) } }
            detail.value = detailResponse?.awaitAll()
            favourites.value = response!!
        }
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


    //delete all

    fun deleteAllRecent(context: Context) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.deleteAllFromTableRecent()
        }
    }

    fun deleteAllFavourite(context: Context) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.deleteAllFromTableLocations()
        }
    }

    //daily detail
    fun getDaily(id: String, date: String) {
        viewModelScope.launch {
            val response = async { Repository().getDailyDetail(id, date) }
            daily.value = response.await()
        }
    }

    //detalji za listu lokacija
    fun getDetailLoc(location: List<Locations>) {
        viewModelScope.launch {
            val detailResponse = location.map { detail -> async { Repository().getSpecLoc(detail.woeid) } }
            detail.value = detailResponse.awaitAll()
        }
    }

    //distance
    fun getLat(context: Context) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            lat.value = db?.weatherDao()?.getBaseCity()
        }
    }

    fun addBaseToDb(context: Context, baseCity: BaseCity) {
        viewModelScope.launch {
            val db = WeatherDatabase.getDatabase(context)
            db?.weatherDao()?.addBaseCity(baseCity)
            getLat(context)
        }
    }


}