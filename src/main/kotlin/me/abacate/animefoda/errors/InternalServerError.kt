package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun InternalServerErrorResponse(message:String?):ResponseStatusException{
    return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,message)
}
