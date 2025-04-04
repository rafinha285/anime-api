package me.abacate.animefoda.repositories

import me.abacate.animefoda.enums.StateName
import me.abacate.animefoda.models.State
import org.springframework.data.jpa.repository.JpaRepository

interface StateRepository: JpaRepository<State, Int> {
    fun existsByName(name: StateName): Boolean
}