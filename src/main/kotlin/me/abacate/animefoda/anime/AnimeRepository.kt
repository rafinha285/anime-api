package me.abacate.animefoda.anime

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface
AnimeRepository: JpaRepository<Anime, UUID> {
    fun findByVisibleTrue(): List<Anime>;
}