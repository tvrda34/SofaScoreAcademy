package com.example.sofascoreacademy.project.model

import java.io.Serializable

class Footballer(
    val name: String,
    val surname: String,
    val age: Int,
    val position: String,
    val club: String,
    val teamRole: TeamRole,
    val apperanceNum: Int,
    val image: String
) : Serializable {
    override fun toString(): String {
        return "$name $surname ($age): pozicija - $position, \n klub - $club,  status - $teamRole, nastupa($apperanceNum) "
    }
}