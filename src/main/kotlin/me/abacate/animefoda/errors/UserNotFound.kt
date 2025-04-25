package me.abacate.animefoda.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "")
class UserNotFound(id: UUID?):RuntimeException("User $id not found")
