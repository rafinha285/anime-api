package me.abacate.animefoda.entities.state

import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.abacate.animefoda.entities.state.StateName

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