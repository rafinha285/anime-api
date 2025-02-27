package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

fun AnimeNotFound(id:Any) : ResponseStatusException{
    return ResponseStatusException(HttpStatus.NOT_FOUND,"Anime $id not found")
}
