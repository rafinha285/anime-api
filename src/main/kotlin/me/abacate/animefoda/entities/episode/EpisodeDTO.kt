package me.abacate.animefoda.entities.episode

import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

data class EpisodeDTO(
    val id: UUID,
    val animeId: UUID,
    val animeTitle: String,
    val seasonId: UUID,
    val seasonTitle: String,
    val dateAdded: LocalDateTime,
    val duration: Double,
    val ending: Int?,
    val epIndex: Int = 1,
    val name: String,
    val openingStart: Int?,
    val openingEnd: Int?,
    val releaseDate: Date,
    val subtitleTracks: List<String>,
    val audioTracks: List<String>,
    val resolution: List<String>
)
