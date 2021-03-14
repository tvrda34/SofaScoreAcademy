package com.example.sofascoreacademy.dz2.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


class SharedViewModel : ViewModel() {
    private val liveList = MutableLiveData<ArrayList<Footballer>>()

    init {
        liveList.value = ArrayList()
    }

    fun addToList(footballer: Footballer) {
        liveList.value?.add(footballer)
    }

    fun getList(): MutableLiveData<ArrayList<Footballer>> {
        return liveList
    }

}