package me.abacate.animefoda.entities.producer

import java.util.UUID

data class ProducerDTO(
    val id: UUID,
    val name: String,
    val description: String,
)
