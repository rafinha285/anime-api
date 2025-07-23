package me.abacate.animefoda.entities.anime

import me.abacate.animefoda.anime.Anime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface
AnimeRepository: JpaRepository<Anime, UUID> {
    @Query(
        value = "SELECT * FROM anime.anime WHERE :genre = ANY(genre)",
        nativeQuery = true
    )
    fun findByGenreContains(@Param("genre") genre: String): List<Anime>
    
    @Query(
        value = "SELECT * FROM anime.anime WHERE :genre = ANY(genre) AND visible = :visible",
        nativeQuery = true
    )
    fun findByGenreContainsAndVisible(@Param("genre") genre: String, @Param("visible") visible: Boolean): List<Anime>
    
    fun findByVisibleTrue(): List<Anime>;
}