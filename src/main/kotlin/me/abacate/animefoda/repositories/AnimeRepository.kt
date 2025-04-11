package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Anime
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AnimeRepository: JpaRepository<Anime,UUID> {
    fun findByVisibleTrue(): List<Anime>;
}
