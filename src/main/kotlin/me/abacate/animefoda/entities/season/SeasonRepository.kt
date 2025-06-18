package me.abacate.animefoda.entities.season

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SeasonRepository: JpaRepository<Season, UUID> {
//    @Query(value = "SELECT s FROM Season s WHERE s.anime_id = :id")
    fun findByAnimeId(animeId: UUID): List<Season>
}