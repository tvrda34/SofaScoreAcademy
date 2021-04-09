package com.example.sofascoreacademy.project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.SpecLoc

import com.example.sofascoreacademy.project.networking.repository.Repository
import kotlinx.coroutines.launch


class SharedViewModel() : ViewModel() {
    private val cityList = MutableLiveData<List<Locations>>()
    private val locDetail = MutableLiveData<SpecLoc>()

    fun getCity(search: String) {
        viewModelScope.launch {
            val response: List<Locations> = Repository().getLocations(search)
            cityList.value = response
        }
    }

    fun getLocData(woeid: Int) {
        viewModelScope.launch {
            val response: SpecLoc = Repository().getSpecLoc(woeid)
            Log.d("coru", response.parent.title)
            locDetail.value = response
        }
    }

    fun getCityList(): MutableLiveData<List<Locations>> {
        return cityList
    }

    fun getLocDetails(): MutableLiveData<SpecLoc> {
        return locDetail
    }


}