package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class AnimeNotFound(id:Any) : RuntimeException("Anime $id not found")
