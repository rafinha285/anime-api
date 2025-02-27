package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun animeNotFound():ResponseStatusException {
    return ResponseStatusException(HttpStatus.NOT_FOUND, "Anime não encontrado")
}
