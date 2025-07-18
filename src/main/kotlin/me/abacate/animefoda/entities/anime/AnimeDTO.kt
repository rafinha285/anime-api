package me.abacate.animefoda.entities.anime

import me.abacate.animefoda.entities.character.CharacterDTO
import me.abacate.animefoda.entities.creator.CreatorDTO
import me.abacate.animefoda.entities.producer.ProducerDTO
import me.abacate.animefoda.entities.season.SeasonDTO
import me.abacate.animefoda.entities.state.StateDTO
import me.abacate.animefoda.entities.studio.StudioDTO
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
    val name2:String,
    val quality:String,
    val rating: Double,
    val visible: Boolean,
    val weekday: String?,
    val producers: List<ProducerDTO>,
    val creators: List<CreatorDTO>,
    val studios: List<StudioDTO>,
    val characters: List<CharacterDTO>,
    val state: StateDTO,
    val releaseDate: LocalDate,
    val seasons: List<SeasonDTO>
)
