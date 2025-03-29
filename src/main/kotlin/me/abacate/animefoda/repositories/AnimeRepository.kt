package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.AnimeModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface AnimeRepository: JpaRepository<AnimeModel,UUID> {}
