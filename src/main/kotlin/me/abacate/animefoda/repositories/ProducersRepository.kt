package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Producer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProducersRepository: JpaRepository<Producer, UUID> {
    fun findByName(name: String?): Optional<Producer>
    fun findByNameIn(names: List<String>): List<Producer>
}
