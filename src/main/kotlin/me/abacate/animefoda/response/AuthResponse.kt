package me.abacate.animefoda.response

import java.util.*

data class AuthResponse (
    val accessToken:String,
    val refreshToken:String,
    val expiresIn:Long,
)
