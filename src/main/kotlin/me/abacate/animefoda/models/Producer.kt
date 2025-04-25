package me.abacate.animefoda.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "producers", schema = "anime")
data class Producer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    val id: UUID? = null,
    
    @Column(name = "name", length = 255, nullable = false)
    val name: String? = null,
    
    @Column(name = "description", columnDefinition = "text", nullable = true)
    val description: String? = null,
)
