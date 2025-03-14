package me.abacate.animefoda.jwt

import me.abacate.animefoda.controllers.post.LoginRequestEntity
import me.abacate.animefoda.models.UserModel
import me.abacate.animefoda.models.UserSession
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.repositories.UserSessionRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

data class GenTokenResponse(
    val jwt:Jwt,
    val refreshToken:String,
    val expiresIn:Long,
)

@Component
class JWTUtil (
        private val userSessionRepository: UserSessionRepository,
        private val userRepository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val jwtEncoder: JwtEncoder
    ){
    
    @Value("\${jwt.secret}")
    private lateinit var secret: String
    
    @Value("\${jwt.expiration}")
    private val expiresIn: Long = 300L
    
    fun generateToken(requestEntity: LoginRequestEntity,userAgent:String):GenTokenResponse{
        val user = userRepository.findByEmail((requestEntity.email))
            ?: throw BadCredentialsException("Invalid email or password")
        if(!user.isLoginCorrect(requestEntity,bCryptPasswordEncoder)){
            throw BadCredentialsException("Invalid email or password")
        }
        
        val now = Instant.now()
        
        val expiresInInstant = now.plusSeconds(expiresIn)
        
        val sessionId = UUID.randomUUID()
        
        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("animefoda")
            .issuedAt(now)
            .subject(user.id.toString())
            .expiresAt(expiresInInstant)
            .claim("session_id",sessionId.toString())
            .build()
        
        val jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims))
        
        val userSession = UserSession(
            sessionId = sessionId,
            userId = user.id,
            userAgent = userAgent,
            webGlRenderer = requestEntity.WebGLRenderer,
            webGlVendor = requestEntity.WebGLVendor,
            timeZone = requestEntity.timeZone,
        )
        
        userSessionRepository.save(userSession);
        
        val refreshToken = generateRefreshToken(user)
        
        return GenTokenResponse(jwtValue,refreshToken, expiresIn)
    }
    
    fun generateRefreshToken(user:UserModel):String{
        val sessionId = UUID.randomUUID()
        val now = Instant.now()
        
        val refreshExpiration =  now.plusSeconds(7*24*3600)
        
        val claims = JwtClaimsSet.builder()
            .issuer("animefoda")
            .issuedAt(now)
            .subject(user.id.toString())
            .expiresAt(refreshExpiration)
            .claim("session_id",sessionId.toString())
            .build()
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
