package me.abacate.animefoda.entities.character

import java.util.UUID

data class CharacterDTO(
    val id: UUID,
    val name: String,
    val role: String,
    val description: String,
)
