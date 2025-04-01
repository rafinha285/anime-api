package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserAnimelist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserAnimelistRepository : JpaRepository<UserAnimelist, UUID> {
    fun findByUserId(@Param("user_id") userId: UUID): List<UserAnimelist>
}
