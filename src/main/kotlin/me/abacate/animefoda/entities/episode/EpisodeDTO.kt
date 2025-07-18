package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.entities.language.LanguageDTO
import java.time.OffsetDateTime
import java.util.Date
import java.util.UUID

data class EpisodeDTO(
    val id: UUID,
    val animeId: UUID,
    val seasonId: UUID,
    val animeTitle: String,
    val seasonTitle: String,
    val name: String,
    val dateAdded: OffsetDateTime,
    val duration: Double,
    val index: Int,
    val ending: Int,
    val openingStart: Int,
    val openingEnd: Int,
    val releaseDate: Date,
    val resolution: String,
    val visible: Boolean,
    val subtitleTracks: List<LanguageDTO>,
    val audioTracks: List<LanguageDTO>,
)
