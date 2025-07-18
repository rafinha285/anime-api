package me.abacate.animefoda.character

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.abacate.animefoda.entities.character.CharacterDTO
import java.util.UUID

@Entity
@Table(name = "characters", schema = "anime")
open class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null,
    
    @Column(name = "name")
    val name:String,
    
    @Column(name = "role")
    val role:String,
    
    @Column(name = "description")
    val description:String,
){
    fun toDTO(): CharacterDTO {
        return CharacterDTO(
            id = id!!,
            name = name,
            role = role,
            description = description,
        )
    }
}