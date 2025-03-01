package me.abacate.animefoda.response

import java.util.*

data class AuthResponse (
    val token: String,
    val session_id: UUID,
    val expires: Date,
)
