package me.abacate.animefoda.entities.anime

import me.abacate.animefoda.character.Character
import me.abacate.animefoda.entities.creator.Creator
import me.abacate.animefoda.entities.producer.Producer
import me.abacate.animefoda.entities.season.SeasonDTO
import me.abacate.animefoda.entities.state.State
import me.abacate.animefoda.entities.studio.Studio
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

data class AnimeDTO(
    val id: UUID,
    val averageEpTime: Double,
    val dateAdded: OffsetDateTime,
    val description: String,
    val genre: List<String>,
    val language: String,
    val name: String,
    val name2: String?,
    val quality: String,
    val rating: Double,
    val visible: Boolean,
    val weekday: String,
    val producers: Set<Producer>,
    val creators: Set<Creator>,
    val studios: Set<Studio>,
    val characters: Set<Character>,
    val state: State,
    val releaseDate: LocalDate,
    val seasons: List<SeasonDTO>
)
