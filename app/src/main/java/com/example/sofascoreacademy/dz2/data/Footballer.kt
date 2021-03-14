package com.example.sofascoreacademy.dz2.data

class Footballer(
    private val name: String,
    private val surname: String,
    private val age: Int,
    private val position: String,
    private val club: String
) {
    override fun toString(): String {
        return "$name $surname($age): $position, $club"
    }
}