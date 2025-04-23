package me.abacate.animefoda.repositories

import me.abacate.animefoda.enums.StateName
import me.abacate.animefoda.models.State
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StateRepository: JpaRepository<State, Int> {
    fun existsByName(name: StateName): Boolean
    fun findByName(name: StateName):Optional<State>
}