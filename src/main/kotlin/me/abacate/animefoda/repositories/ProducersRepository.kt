package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.ProducersModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProducersRepository: JpaRepository<ProducersModel, UUID>
