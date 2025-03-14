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
    
//    fun decodeJwt(token: String): Claims {
//        return Jwts.parser()
//            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
//            .clockSkewSeconds(300)
//            .build()
//            .parseSignedClaims(token)
//            .payload
//    }
    
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
    
//    fun generateToken(userSession:LoginRequestEntity,userAgent:String): AuthResponse {
//        val expirationDate = Date(System.currentTimeMillis() + expiration)
//
//        val user = userRepository.findByEmailAndPassword(userSession.email, userSession.password)
//            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
//
//        val sessionId = UUID.randomUUID()
//
//        val key = Keys.hmacShaKeyFor(secret.toByteArray())
//
//        val userToken = UserToken(
//            _id = user.id,
//            username = user.username,
//            expires_at = expirationDate,
//            session_id = UUID.randomUUID()
//        )
//
//        val userSessionEntity = UserSession(
//            sessionId = sessionId,
//            userId = user.id,
//            createdAt = LocalDateTime.now(),
//            expiresAt = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
//            userAgent = userAgent, // Se tiver o user-agent disponível, atribua aqui
//            webGlVendor = userSession.WebGLVendor,
//            webGlRenderer = userSession.WebGLRenderer,
//            enabled = true,
//            timeZone = userSession.timeZone
//        )
//
//
//        val claims = mutableMapOf<String, Any>(
//            "_id" to userToken._id,
//            "username" to userToken.username,
//            "session_id" to sessionId,
//        )
//
//        val jwtToken = Jwts.builder()
//            .claims(claims)
//            .expiration(expirationDate)
//            .signWith(key)
//            .compact()
//
//        userSessionRepository.save(userSessionEntity)
//
//        return AuthResponse(
//            token = jwtToken,
//            session_id = sessionId,
//            expires = expirationDate
//        )
//    }

//    fun checkToken(
//        token: String,
//        request: HttpServletRequest,
//        response: HttpServletResponse
//    ) {
//        val tokenn = decodeJwt(token)
//        if(tokenn == null){
//            throw UnauthorizedResponse()
//        }
//        val userId = UUID.fromString(tokenn.get("_id") as String)
//        val username = tokenn.get("username") as String
//        val sessionId = UUID.fromString(tokenn.get("session_id") as String)
//
//
//        val row = userSessionRepository.findBySessionIdAndEnabled(sessionId,userId)
//
//        if(row == null){
//            throw UnauthorizedResponse()
//        }
//
//
//        if(row.expiresAt < LocalDateTime.now()) {
//            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired")
//        }
//
//        val userAgent = request.getHeader("User-Agent") ?: ""
//        val timeZone = request.getHeader("timeZone") ?: ""
//        val webGlRenderer = request.getHeader("webGlRenderer") ?: ""
//        val webGlVendor = request.getHeader("webGlVendor") ?: ""
//
//        if(userAgent.isEmpty() || timeZone.isEmpty() || webGlRenderer.isEmpty() || webGlVendor.isEmpty()){
//            throw BadRequestResponse("Request Header missing")
//        }
//        if(
//            !(row.userAgent == userAgent &&
//            row.timeZone == timeZone &&
//            row.webGlRenderer == webGlRenderer &&
//            row.webGlVendor == webGlVendor)
//        ){
//            throw UnauthorizedResponse()
//        }
//        val cookie = Cookie("token", genToken(tokenn))
//        response.addCookie(cookie)
//    }
}
