package me.abacate.animefoda.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "creators", schema = "anime")
data class Creator(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    val id: UUID? = null,
    
    @Column(name = "name", length = 255)
    val name: String? = null,
    
    @Column(name = "description", columnDefinition = "text")
    val description: String? = null,
)
