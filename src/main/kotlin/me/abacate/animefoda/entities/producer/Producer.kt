package me.abacate.animefoda.entities.producer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

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