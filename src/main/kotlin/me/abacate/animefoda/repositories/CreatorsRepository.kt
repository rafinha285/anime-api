package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.CreatorsModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CreatorsRepository : JpaRepository<CreatorsModel, UUID>
