package me.abacate.animefoda.entities.creator

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "creators", schema = "anime")
open class Creator(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", nullable = false)
    val id: UUID? = null,
    
    @Column(name = "name", length = 255, nullable = false)
    val name: String? = null,
    
    @Column(name = "description", columnDefinition = "text")
    val description: String? = null,
){
    fun toDTO(): CreatorDTO {
        return CreatorDTO(
            id = id!!,
            name = name!!,
            description = description
        )
    }
}