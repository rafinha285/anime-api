package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun BadRequestResponse(): ResponseStatusException {
    return ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request")
}
