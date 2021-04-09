package com.example.sofascoreacademy.project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc

import com.example.sofascoreacademy.project.networking.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class SharedViewModel() : ViewModel() {
    private val cityList = MutableLiveData<Response<List<Locations>>>()
    private val locDetail = MutableLiveData<Response<SpecLoc>>()

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


}