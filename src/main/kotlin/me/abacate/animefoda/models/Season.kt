package me.abacate.animefoda.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "seasons", schema = "anime")
data class Season(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID? = null,
    
    @Column(name = "name", length = 255)
    val name: String,
    
    @Column(name = "anime_id",)
    val anime_id: UUID,
    
    @Column(name = "index")
    val index: Int,
)
