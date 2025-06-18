package me.abacate.animefoda.entities.studio

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface StudiosRepository : JpaRepository<Studio, UUID> {
    fun findByName(name: String): Optional<Studio>
    fun findByNameIgnoreCase(name: String): Optional<Studio>
    fun findByNameIn(names: List<String>): List<Studio>
}