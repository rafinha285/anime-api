package me.abacate.animefoda.entities.state

import me.abacate.animefoda.entities.state.StateName
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StateRepository: JpaRepository<State, Int> {
    fun existsByName(name: StateName): Boolean
    fun findByName(name: StateName): Optional<State>
}