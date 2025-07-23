package me.abacate.animefoda.entities.anime

import java.util.UUID

data class AnimeSummaryDTO(
    val id: UUID,
    val averageEpTime: Double,
    val name: String,
    val name2: String?,
    val genre: List<String>,
    val description: String,
    val rating: Double?,
    val weekday: String?,
    val visible: Boolean,
)
