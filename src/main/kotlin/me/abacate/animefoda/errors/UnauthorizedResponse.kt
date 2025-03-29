package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class UnauthorizedResponse(): RuntimeException("Unauthorized") {
//    return ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
}
