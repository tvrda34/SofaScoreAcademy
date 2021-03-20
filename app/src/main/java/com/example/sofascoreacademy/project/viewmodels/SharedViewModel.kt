package com.example.sofascoreacademy.project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sofascoreacademy.project.model.Footballer
import com.example.sofascoreacademy.project.model.TeamRole


class SharedViewModel : ViewModel() {
    private val liveList = MutableLiveData<ArrayList<Footballer>>()

    init {
        liveList.value = arrayListOf(
                Footballer("Luka", "Modrić", 35, "vezni", "Real Madrid", TeamRole.Legend, 133),
                Footballer("Kristijan", "Lovrić", 25, "napadač", "HNK Gorica", TeamRole.RisingStar, 0),
                Footballer("Franko", "Andrijašević", 29, "vezni", "HNK Rijeka", TeamRole.Maestro, 0),
                Footballer("Ivica", "Ivušić", 26, "golman", "NK Osijek", TeamRole.RisingStar, 0)
        )
    }

    fun addToList(footballer: Footballer) {
        liveList.value?.add(footballer)
    }

    fun getList(): MutableLiveData<ArrayList<Footballer>> {
        return liveList
    }

}