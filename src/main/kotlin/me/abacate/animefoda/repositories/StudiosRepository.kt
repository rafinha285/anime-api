package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Studio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StudiosRepository : JpaRepository<Studio, UUID>{
    fun findByName(name: String): Optional<Studio?>
    fun findByNameIn(names: List<String>): List<Studio>
}
