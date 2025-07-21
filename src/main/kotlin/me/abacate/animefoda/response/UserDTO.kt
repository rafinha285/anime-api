package me.abacate.animefoda.response

import me.abacate.animefoda.entities.role.Role
import me.abacate.animefoda.entities.user.animelist.AnimelistDTO
import java.time.LocalDate
import java.util.UUID

data class UserDTO(
    val id: UUID?,
    val name: String,
    val email: String,
    val surname: String,
    val username: String,
    val birthdate: LocalDate,
    val roles: Set<Role>,
    val animelist: Set<AnimelistDTO>?,
    val superuser: Boolean
)
