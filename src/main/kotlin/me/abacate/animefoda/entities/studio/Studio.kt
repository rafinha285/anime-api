package me.abacate.animefoda.entities.studio

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "studios", schema = "anime")
data class Studio(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    val id: UUID? = null,
    
    @Column(name = "name", length = 255)
    val name: String? = null,
    
    @Column(name = "description", columnDefinition = "text")
    val description: String? = null,
)