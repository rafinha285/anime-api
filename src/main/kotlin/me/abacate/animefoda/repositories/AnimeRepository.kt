package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.AnimeModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AnimeRepository: JpaRepository<AnimeModel,UUID>
