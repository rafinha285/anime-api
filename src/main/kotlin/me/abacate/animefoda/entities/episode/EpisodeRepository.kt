package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.anime.Anime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional
import java.util.UUID

interface EpisodeRepository: JpaRepository<Episode, UUID>{
    fun findByIdAndVisible(id: UUID, visible: Boolean = true): Episode
    @Query(
        "SELECT * FROM anime.episodes WHERE visible = true ORDER BY date_added DESC LIMIT :limit",
        nativeQuery = true
    )
    fun findByVisibleTrue(@Param("limit") limit: Int? = 10): List<Episode>
    fun findByAnime(anime: Anime): List<Episode>
    fun findByAnimeAndVisibleTrue(anime: Anime): List<Episode>
    @Query(
        value = "SELECT * FROM anime.episodes ORDER BY date_added DESC LIMIT :limit",
        nativeQuery = true
    )
    fun findByDateAddedAndLimit(@Param("limit") limit: Int? = 10): List<Episode>
}