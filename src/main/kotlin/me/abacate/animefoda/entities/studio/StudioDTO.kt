package me.abacate.animefoda.entities.studio

import java.util.UUID

data class StudioDTO(
    val id: UUID,
    val name: String,
    val description: String?
)