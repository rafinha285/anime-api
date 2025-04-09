package me.abacate.animefoda.response

import org.springframework.security.oauth2.jwt.Jwt

data class GenTokenResponse(
    val jwt: Jwt,
    val refreshToken:String,
    val expiresIn:Long,
)
