package me.abacate.animefoda.entities.role

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.abacate.animefoda.entities.role.RoleName

@Entity
@Table(name = "roles", schema = "users")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    
    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    val name: RoleName,
){
    protected constructor() : this(name = RoleName.ROLE_USER)
}