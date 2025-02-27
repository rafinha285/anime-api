package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.StudiosModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudiosRepository : JpaRepository<StudiosModel, UUID>
