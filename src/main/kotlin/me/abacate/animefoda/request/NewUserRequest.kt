package me.abacate.animefoda.request

import java.time.LocalDate

data class NewUserRequest(
    val email:String,
    val name:String,
    val surname:String,
    val username:String,
    val birthdate: LocalDate,
    val password: String,
)