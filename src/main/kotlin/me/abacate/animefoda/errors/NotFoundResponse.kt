package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFoundResponse(id: Any): RuntimeException("Not found: $id")