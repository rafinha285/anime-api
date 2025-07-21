package me.abacate.animefoda.entities.user.animelist

import me.abacate.animefoda.entities.anime.AnimeDTO
import me.abacate.animefoda.enums.PriorityAnimelist
import me.abacate.animefoda.enums.StateAnimelist
import java.time.LocalDateTime
import java.util.UUID

data class AnimelistDTO(
    val userId: UUID,
    val anime: AnimeDTO,
    val status: StateAnimelist,
    val startDate: LocalDateTime,
    val finishDate: LocalDateTime?,
    val rate: Double?,
    val priority: PriorityAnimelist
)
