package me.abacate.animefoda.entities.season

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import me.abacate.animefoda.entities.episode.Episode
import java.util.UUID

@Entity
@Table(name = "seasons", schema = "anime")
data class Season(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null,
    
    @Column(name = "name", length = 255, nullable = false)
    val name: String? = null,
    
    @Column(name = "anime_id", nullable = false)
    val animeId: UUID? = null,
    
    @Column(name = "index", nullable = false)
    val index: Int? = null,
    
    @OneToMany(mappedBy = "season", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val episodes: MutableList<Episode> = mutableListOf()
)