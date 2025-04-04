package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.enums.RoleName

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
