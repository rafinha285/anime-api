package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "")
class InternalServerErrorResponse(message:String?):RuntimeException(message) {
//    return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,message)
}
