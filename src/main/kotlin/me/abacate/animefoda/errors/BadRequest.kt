package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun BadRequestResponse(reason: String? = null): ResponseStatusException {
    return ResponseStatusException(HttpStatus.BAD_REQUEST, reason)
}
