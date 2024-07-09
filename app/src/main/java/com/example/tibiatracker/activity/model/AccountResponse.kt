package com.example.tibiatracker.activity.model


import android.text.style.CharacterStyle
import java.io.Serializable

data class AccountResponse(
    val _id: String,
    val contaID: Int,
    val contaEmail: String,
    val contaNome: String,
    val contaChar: String,
    val __v: Int
) : Serializable

