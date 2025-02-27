package me.abacate.animefoda.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "producers", schema = "anime")
data class ProducersModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID(),
    
    @Column(name = "name", length = 255)
    val name: String = "",
    
    @Column(name = "description", columnDefinition = "text")
    val description: String = "",
)
