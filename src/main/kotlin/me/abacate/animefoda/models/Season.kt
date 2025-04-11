package me.abacate.animefoda.models

import jakarta.persistence.*
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
)
