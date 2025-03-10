package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

fun UserNotFound(id: UUID?):ResponseStatusException {
    if(id != null) {
        return ResponseStatusException(HttpStatus.NOT_FOUND,"User $id not Found")
    }
    return ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
}
