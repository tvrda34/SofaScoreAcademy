package com.example.sofascoreacademy.project.model

class Footballer(
        private val name: String,
        private val surname: String,
        private val age: Int,
        private val position: String,
        private val club: String,
        private val teamRole: TeamRole,
        private val apperanceNum: Int
) {
    override fun toString(): String {
        return "$name $surname ($age): pozicija - $position, \n klub - $club,  status - $teamRole, nastupa($apperanceNum) "
    }
}