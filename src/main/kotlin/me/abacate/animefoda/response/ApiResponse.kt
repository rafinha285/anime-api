package me.abacate.animefoda.response

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T? = null,
    val message: String? = null
)
