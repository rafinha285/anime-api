package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Creator
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface CreatorsRepository : JpaRepository<Creator, UUID>{
    fun findByName(name: String): Optional<Creator?>
}
