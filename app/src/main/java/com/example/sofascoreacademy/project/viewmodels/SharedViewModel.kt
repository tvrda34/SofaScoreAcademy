package com.example.sofascoreacademy.project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sofascoreacademy.project.model.Footballer
import com.example.sofascoreacademy.project.model.TeamRole


class SharedViewModel : ViewModel() {
    private val liveList = MutableLiveData<ArrayList<Footballer>>()

    init {
        liveList.value = arrayListOf(
            Footballer(
                "Luka", "Modrić", 35, "vezni", "Real Madrid", TeamRole.Legend, 133,
                "https://hns-cff.hr/files/images/_resized/0000033475_660_375_cut.jpg"
            ),
            Footballer(
                "Kristijan", "Lovrić", 25, "napadač", "HNK Gorica", TeamRole.RisingStar, 0,
                "https://gorica.info/wp-content/uploads/2020/09/lovric-e1600701269856.jpg"
            ),
            Footballer(
                "Franko", "Andrijašević", 29, "vezni", "HNK Rijeka", TeamRole.Maestro, 2,
                "https://hns-cff.hr/files/images/_resized/0000020498_660_375_cut.jpg"
            ),
            Footballer(
                "Mateo", "Kovačić", 26, "vezni", "Chelsea F.C.", TeamRole.Superstar, 61,
                "https://hns-cff.hr/files/images/_resized/0000032740_660_375_cut.jpg"
            )
        )
    }

    fun addToList(footballer: Footballer) {
        liveList.value?.add(footballer)
    }

    fun getList(): MutableLiveData<ArrayList<Footballer>> {
        return liveList
    }

}