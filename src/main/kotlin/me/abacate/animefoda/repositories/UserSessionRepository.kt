package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserSession
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserSessionRepository : CrudRepository<UserSession, UUID> {
    fun findBySessionIdAndEnabled(sessionId: UUID, enabled: Boolean): UserSession?
}
