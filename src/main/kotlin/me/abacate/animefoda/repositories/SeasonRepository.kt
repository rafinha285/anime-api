package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.SeasonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SeasonRepository: JpaRepository<SeasonModel, UUID> {
    @Query(value = "SELECT s FROM SeasonModel s WHERE s.anime_id = :id")
    fun findByAnimeId(@Param("id") id: UUID): List<SeasonModel>
}
