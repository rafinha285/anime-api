package me.abacate.animefoda.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.controllers.post.LoginRequestEntity
import me.abacate.animefoda.errors.BadRequestResponse
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
    private val expiration: Long = 604800000
    
    fun decodeJwt(token: String): Claims {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .clockSkewSeconds(300)
            .build()
            .parseSignedClaims(token)
            .payload
    }
    
    fun generateToken(userSession:LoginRequestEntity,userAgent:String): AuthResponse {
        val expirationDate = Date(System.currentTimeMillis() + expiration)
        
        val user = userRepository.findByEmailAndPassword(userSession.email, userSession.password)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
        
        val sessionId = UUID.randomUUID()
        
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        
        val userToken = UserToken(
            _id = user.id,
            username = user.username,
            expires_at = expirationDate,
            session_id = UUID.randomUUID()
        )
        
        val userSessionEntity = UserSession(
            sessionId = sessionId,
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
            "session_id" to sessionId,
        )
        
        val jwtToken = Jwts.builder()
            .claims(claims)
            .expiration(expirationDate)
            .signWith(key)
            .compact()
        
        userSessionRepository.save(userSessionEntity)
        
        return AuthResponse(
            token = jwtToken,
            session_id = sessionId,
            expires = expirationDate
        )
    }
    
    fun genToken(claims: Claims): String? {
        val expirationDate = Date(System.currentTimeMillis() + expiration)
        return Jwts.builder()
            .claims(claims)
            .expiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }
    
    fun checkToken(
        token: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val tokenn = decodeJwt(token)
        if(tokenn == null){
            throw UnauthorizedResponse()
        }
        val userId = UUID.fromString(tokenn.get("_id") as String)
        val username = tokenn.get("username") as String
        val sessionId = UUID.fromString(tokenn.get("session_id") as String)
        
        
        val row = userSessionRepository.findBySessionIdAndEnabled(sessionId,userId)
        
        if(row == null){
            throw UnauthorizedResponse()
        }
        
        
        if(row.expiresAt < LocalDateTime.now()) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired")
        }
        
        val userAgent = request.getHeader("User-Agent") ?: ""
        val timeZone = request.getHeader("timeZone") ?: ""
        val webGlRenderer = request.getHeader("webGlRenderer") ?: ""
        val webGlVendor = request.getHeader("webGlVendor") ?: ""
        
        if(userAgent.isEmpty() || timeZone.isEmpty() || webGlRenderer.isEmpty() || webGlVendor.isEmpty()){
            throw BadRequestResponse("Request Header missing")
        }
        if(
            !(row.userAgent == userAgent &&
            row.timeZone == timeZone &&
            row.webGlRenderer == webGlRenderer &&
            row.webGlVendor == webGlVendor)
        ){
            throw UnauthorizedResponse()
        }
        val cookie = Cookie("token", genToken(tokenn))
        response.addCookie(cookie)
    }
}
