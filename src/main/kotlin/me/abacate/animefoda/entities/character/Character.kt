package me.abacate.animefoda.character

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "characters", schema = "anime")
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID,
    
    @Column(name = "name")
    val name:String,
    
    @Column(name = "role")
    val role:String,
    
    @Column(name = "description")
    val description:String,
)