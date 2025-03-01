package me.abacate.animefoda.models

import java.util.*

data class UserToken (
    val _id:UUID,
    val username:String,
    val expires_at: Date,
    val session_id:UUID,
)
