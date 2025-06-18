package me.abacate.animefoda.entities.user.animelist

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserAnimelistRepository : JpaRepository<UserAnimelist, UUID> {
    fun findByUserId(@Param("user_id") userId: UUID): List<UserAnimelist>
}