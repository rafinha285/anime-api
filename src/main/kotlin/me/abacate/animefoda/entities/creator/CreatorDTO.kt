package me.abacate.animefoda.entities.creator

import java.util.UUID

data class CreatorDTO(
    val id: UUID,
    val name:String,
    val description: String?,
)
