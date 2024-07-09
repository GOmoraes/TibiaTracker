package com.example.tibiatracker.activity.model


import android.text.style.CharacterStyle
import java.io.Serializable

data class CharPorNomeResponse(
    val character : Character1
) : Serializable

data class Character1(
    val character : Character2
) : Serializable

data class Character2(
    val account_status: String?,
    val achievement_points: Int?,
    val comment: String?,
    val level: Int?,
    val name: String?,
    val world: String?,
    val last_login: String?,
    val traded: Boolean?,
    val vocation: String?

    ) : Serializable
