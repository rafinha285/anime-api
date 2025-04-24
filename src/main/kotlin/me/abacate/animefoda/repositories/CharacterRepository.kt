package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Character
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CharacterRepository: JpaRepository<Character, UUID> {
}