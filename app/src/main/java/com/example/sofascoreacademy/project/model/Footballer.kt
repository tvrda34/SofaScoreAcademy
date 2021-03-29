package com.example.sofascoreacademy.project.model

import com.example.sofascoreacademy.R
import java.io.Serializable

class Footballer(
        val name: String,
        val surname: String,
        val age: Int,
        val position: Position,
        val club: String,
        val teamRole: TeamRole,
        val apperanceNum: Int,
        val image: String
) : Serializable {
    override fun toString(): String {
        return "$name $surname ($age): pozicija - $position, \n klub - $club,  status - $teamRole, nastupa($apperanceNum) "
    }
}

enum class TeamRole {
    YouthProspect, Superstar, BadBoy, TeamPlayer, RisingStar, Maestro, Legend
}

enum class Position(val id: Int) {
    Goalkeeper(R.string.golman), Defender(R.string.branic), Midfielder(R.string.vezni), Forward(R.string.napadac);
}
