package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.entities.language.LanguageDTO
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.Date
import java.util.UUID

data class EpisodeDTO(
    val id: UUID,
    val animeId: UUID,
    val animeTitle: String,
    val seasonId: UUID,
    val seasonTitle: String,
    val dateAdded: OffsetDateTime,
    val duration: Double,
    val ending: Int?,
    val epIndex: Int = 1,
    val name: String,
    val openingStart: Int?,
    val openingEnd: Int?,
    val releaseDate: Date,
    val subtitleTracks: List<LanguageDTO>,
    val audioTracks: List<LanguageDTO>,
    val resolution: List<String>,
    val visible: Boolean,
)
