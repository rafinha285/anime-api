package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.anime.Anime
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface EpisodeRepository: JpaRepository<Episode, UUID>{
    fun findByIdAndVisible(id: UUID, visible: Boolean = true): Episode
    fun findByVisibleTrue(): List<Episode>
    fun findByAnime(anime: Anime): List<Episode>
    fun findByAnimeAndVisibleTrue(anime: Anime): List<Episode>
}