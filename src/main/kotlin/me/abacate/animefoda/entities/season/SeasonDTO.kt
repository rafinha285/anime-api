package me.abacate.animefoda.entities.season

import me.abacate.animefoda.entities.episode.EpisodeDTO
import java.util.UUID

data class SeasonDTO(
    val id: UUID,
    val name: String,
    val animeId: UUID,
    val index: Int,
    val episodes: List<EpisodeDTO>
)
