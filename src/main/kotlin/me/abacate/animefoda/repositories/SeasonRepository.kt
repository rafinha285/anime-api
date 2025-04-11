package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Season
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SeasonRepository: JpaRepository<Season, UUID> {
//    @Query(value = "SELECT s FROM Season s WHERE s.anime_id = :id")
    fun findByAnimeId(animeId:UUID): List<Season>
}
