package me.abacate.animefoda.response

object AdminAccess {
    fun adminAccess(admin: Boolean): String?{
        return if(admin) "Admin access" else null
    }
}