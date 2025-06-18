package me.abacate.animefoda.services

import me.abacate.animefoda.entities.user.*
import me.abacate.animefoda.entities.user.session.*
import me.abacate.animefoda.request.LoginRequest
import me.abacate.animefoda.response.GenTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JWTService(
    private val userSessionRepository: UserSessionRepository,
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
){
    
    @Value("\${jwt.secret}")
    private lateinit var secret: String
    
    @Value("\${jwt.expiration}")
    private val expiresIn: Long = 172800L
    
    fun generateToken(requestEntity: LoginRequest, userAgent:String): GenTokenResponse {
        val user = userRepository.findByEmail((requestEntity.email))
            ?: throw BadCredentialsException("Invalid email or password")
        if(!user.isLoginCorrect(requestEntity,bCryptPasswordEncoder)){
            throw BadCredentialsException("Invalid email or password")
        }
        
        val now = Instant.now()
        
        val expiresInInstant = now.plusSeconds(expiresIn)
        
        val refreshSessionId = UUID.randomUUID()
//        val accessSessionId = UUID.randomUUID()
        
        println("refreshId: $refreshSessionId")
//        println("accessSessionId: $accessSessionId")
        
        //criar o refresh token
        val refreshToken = generateRefreshToken(user,refreshSessionId)
        
        //criar o access token
        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("animefoda")
            .issuedAt(now)
            .subject(user.id.toString())
            .expiresAt(expiresInInstant)
            .claim("session_id",refreshSessionId.toString())
//            .claim("session_id", accessSessionId.toString())
            .build()
        
        val jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims))
        
        val userSession = user.id?.let {
            UserSession(
                sessionId = refreshSessionId,
                userId = it,
                userAgent = userAgent,
                webGlRenderer = requestEntity.WebGLRenderer,
                webGlVendor = requestEntity.WebGLVendor,
                timeZone = requestEntity.timeZone,
            )
        }
        
        if (userSession != null) {
            userSessionRepository.save(userSession)
        }
        
        return GenTokenResponse(jwtValue,refreshToken, expiresIn)
    }
    
    fun generateRefreshToken(user:User, sessionId:UUID):String{
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
    
    fun refreshAccessToken(refreshToken: String,userAgent: String):GenTokenResponse{
        val jwt = try{
            jwtDecoder.decode(refreshToken)
        }catch(e: JwtException){
            throw BadCredentialsException("Invalid refresh token")
        }
        println("jwt expiresAt: "+Date.from(jwt.expiresAt))
        println("jwt subject: "+jwt.subject)
        println("jwt sessionId: "+jwt.getClaim<String>("session_id").toString())
        val userId = UUID.fromString(jwt.subject)
        val refreshSessionId = UUID.fromString(jwt.getClaim<String>("session_id"))
//        val accessSessionId = UUID.fromString(jwt.getClaim<String>("session_id"))
        
        
        
        //checa se a session do refresh token existe e é valida
        userSessionRepository.findBySessionIdAndEnabled(refreshSessionId,userId)
            ?:throw BadCredentialsException("Invalid Session or expired refresh token")
        
        val now = Instant.now()
        val expiresInInstant = now.plusSeconds(expiresIn)
        
        val newClaims = JwtClaimsSet.builder()
            .issuer("animefoda")
            .issuedAt(now)
            .subject(userId.toString())
            .expiresAt(expiresInInstant)
            .claim("session_id",refreshSessionId.toString())
//            .claim("refresh_session_id",refreshSessionId.toString())
            .build()
        
        val newJwtValue = jwtEncoder.encode(JwtEncoderParameters.from(newClaims))
        
        return GenTokenResponse(newJwtValue, refreshToken, expiresIn)
    }
    
}
