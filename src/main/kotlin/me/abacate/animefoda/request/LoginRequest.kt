package me.abacate.animefoda.request

data class LoginRequest(
    val email: String,
    val password: String,
    val timeZone: String,
    val WebGLVendor: String,
    val WebGLRenderer: String,
)
