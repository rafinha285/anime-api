package me.abacate.animefoda.response

data class OAuth2TokenResponse(
    val access_token: String,
    val token_type: String = "Bearer",
    val expires_in: Long, // tempo em segundos
    val refresh_token: String? = null // se implementar refresh token
)
