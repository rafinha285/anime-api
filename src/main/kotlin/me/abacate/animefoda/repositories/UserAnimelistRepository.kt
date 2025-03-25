package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserAnimelistModel
import me.abacate.animefoda.serialized.UserAnimeListId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserAnimelistRepository : JpaRepository<UserAnimelistModel, UUID> {
    fun findByUserId(@Param("user_id") userId: UUID): List<UserAnimelistModel>
}
