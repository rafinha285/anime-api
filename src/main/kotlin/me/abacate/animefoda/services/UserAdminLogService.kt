package me.abacate.animefoda.services

import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.UserAdminLog
import me.abacate.animefoda.repositories.UserAdminLogRepository
import me.abacate.animefoda.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserAdminLogService(
    private val logRepository: UserAdminLogRepository,
    private val userRepository: UserRepository
) {
    fun logAdminCommand(command: String){
        val authentication = SecurityContextHolder.getContext().authentication
        
        if (authentication == null || authentication.principal !is Jwt) {
            throw IllegalStateException("Usuário não autenticado via JWT")
        }
        
        val currentJwt = authentication.principal as Jwt
        
        val user = userRepository.findById(UUID.fromString(currentJwt.subject)).orElseThrow { UnauthorizedResponse() }
        val sessionId = UUID.fromString(currentJwt.getClaim<String>("session_id"))
        
        val logEntry = UserAdminLog(
            command = command,
            sessionId = sessionId,
            user = user,
        )
        
        logRepository.save(logEntry)
    }
}