package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun UnauthorizedResponse(): ResponseStatusException {
    return ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
}
