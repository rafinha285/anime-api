package me.abacate.animefoda.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "characters", schema = "anime")
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id:UUID,
    
    @Column(name = "name")
    val name:String,
    
    @Column(name = "role")
    val role:String,
    
    @Column(name = "description")
    val description:String,
)
