package me.abacate.animefoda.entities.user.session

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserSessionRepository : JpaRepository<UserSession, UUID> {
    @Query("""
        SELECT us
        FROM UserSession us
        WHERE us.sessionId = :sessionId
        AND us.user.id = :userId
        AND us.enabled = :enabled
    """)
    fun findBySessionIdAndEnabled(
        @Param("sessionId") sessionId: UUID,
        @Param("userId") userId: UUID,
        @Param("enabled") enabled: Boolean = true
    ): UserSession?
}