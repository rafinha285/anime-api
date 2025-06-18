package me.abacate.animefoda.entities.creator

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface CreatorsRepository : JpaRepository<Creator, UUID> {
    fun findByName(name: String): Optional<Creator>
    fun findByNameIgnoreCase(name: String): Optional<Creator>
    fun findByNameIn(names: List<String>): List<Creator>
}