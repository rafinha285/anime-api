package me.abacate.animefoda.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import me.abacate.animefoda.controllers.post.LoginRequestEntity
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.UserSession
import me.abacate.animefoda.models.UserToken
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.repositories.UserSessionRepository
import me.abacate.animefoda.response.AuthResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@Component
class JWTUtil (
    val userSessionRepository: UserSessionRepository,
    val userRepository: UserRepository
    ){
    
    @Value("\${jwt.secret}")
    private lateinit var secret: String
    
    @Value("\${jwt.expiration}")
    private val expiration: Long = 60000
    
    fun decodeJwt(token: String): Claims? {
        return try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseSignedClaims(token)
                .body
        } catch (e: Exception) {
            println("Erro ao decodificar JWT: ${e.message}")
            null
        }
    }
    
    fun generateToken(userSession:LoginRequestEntity,userAgent:String): AuthResponse {
        val expirationDate = Date(System.currentTimeMillis() + expiration)
        
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        
        val user = userRepository.findByEmailAndPassword(userSession.email, userSession.password)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
        
        
        val userToken = UserToken(
            _id = user.id,
            username = user.username,
            expires_at = expirationDate,
            session_id = UUID.randomUUID()
        )
        
        val userSessionEntity = UserSession(
            userId = user.id,
            createdAt = LocalDateTime.now(),
            expiresAt = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            userAgent = userAgent, // Se tiver o user-agent disponível, atribua aqui
            webGlVendor = userSession.WebGLVendor,
            webGlRenderer = userSession.WebGLRenderer,
            enabled = true,
            timeZone = userSession.timeZone
        )
        
        
        val claims = mutableMapOf<String, Any>(
            "_id" to userToken._id,
            "username" to userToken.username,
            "session_id" to userToken.session_id
        )
        
        val jwtToken = Jwts.builder()
            .claims(claims)
            .expiration(expirationDate)
            .signWith(key)
            .compact()
        
        userSessionRepository.save(userSessionEntity)
        
        return AuthResponse(
            token = jwtToken,
            session_id = userToken.session_id,
            expires = expirationDate
        )
    }
    fun checkToken(
        token: String,
        userAgent:String,
        timezone:String,
        webGlRenderer:String,
        webGlVendor:String
    ): Boolean {
        val tokenn = decodeJwt(token)
        if(tokenn == null){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token not found")
        }
        val userId = UUID.fromString(tokenn.get("_id") as String)
        val username = tokenn.get("username") as String
        val sessionId = UUID.fromString(tokenn.get("session_id") as String)
        
        
        val row = userSessionRepository.findBySessionIdAndEnabled(sessionId = sessionId, enabled = true)
        ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session not found")
        
        if(row.expiresAt > LocalDateTime.now()) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired")
        }
        if(
            row.userAgent == userAgent &&
            row.timeZone == timezone &&
            row.webGlRenderer == webGlRenderer &&
            row.webGlVendor == webGlVendor
        ){
            return true;
        }else{
            throw UnauthorizedResponse()
        }
    }
}
