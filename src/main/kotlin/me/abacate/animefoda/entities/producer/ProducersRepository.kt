package me.abacate.animefoda.entities.producer

import me.abacate.animefoda.entities.producer.Producer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ProducersRepository: JpaRepository<Producer, UUID> {
    fun findByName(name: String?): Optional<Producer>
    fun findByNameIgnoreCase(name: String): Optional<Producer>
    fun findByNameIn(names: List<String>): List<Producer>
}