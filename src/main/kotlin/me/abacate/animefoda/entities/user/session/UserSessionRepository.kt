package me.abacate.animefoda.entities.user.session

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserSessionRepository : JpaRepository<UserSession, UUID> {
    @Query("SELECT u FROM UserSession u WHERE u.sessionId = :sessionId AND u.userId = :userId AND u.enabled = :enabled")
    fun findBySessionIdAndEnabled(
        @Param("sessionId") sessionId: UUID,
        @Param("userId") userId: UUID,
        @Param("enabled") enabled: Boolean = true
    ): UserSession?
}