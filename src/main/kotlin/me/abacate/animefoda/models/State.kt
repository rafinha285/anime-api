package me.abacate.animefoda.models

import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.*
import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.enums.StateName

@Entity
@Table(name = "state", schema = "anime")
data class State (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = 0,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    val name: StateName,
){
    protected constructor() : this(name = StateName.NOT_AIRING)
    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(value: String): State {
            // Cria a instância com id nulo (ou default) e o enum convertido
            return State(name = StateName.valueOf(value))
        }
    }
}