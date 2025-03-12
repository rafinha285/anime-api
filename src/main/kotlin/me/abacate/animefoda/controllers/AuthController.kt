package me.abacate.animefoda.controllers

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.controllers.post.LoginRequestEntity
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.response.AuthResponse
import me.abacate.animefoda.response.OAuth2TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil
) {
    @PostMapping("/oauth/token")
    fun login(
        @RequestBody loginRequest: LoginRequestEntity,
        @RequestHeader(name = "User-Agent") userAgent: String,
        response: HttpServletResponse
    ): OAuth2TokenResponse {
        val user = userRepository.findByEmailAndPassword(
            email = loginRequest.email,
            password = loginRequest.password
        ) ?: throw UnauthorizedResponse()
        
        if (userAgent.isBlank()) {
            throw BadRequestResponse("User-Agent header is required")
        }
        
        // Gera o token JWT (AuthResponse contém o token, data de expiração, etc.)
        val authResponse: AuthResponse = jwtUtil.generateToken(loginRequest, userAgent)
        
        // Opcional: você pode definir um cookie se desejar
         val cookie = Cookie("token", authResponse.token)
         cookie.path = "/"
         response.addCookie(cookie)
        
        // Calcula o tempo de expiração em segundos com base na data de expiração do token
        val currentTimeMillis = System.currentTimeMillis()
        val expiresInSeconds = (authResponse.expires.time - currentTimeMillis) / 1000
        
        return OAuth2TokenResponse(
            access_token = authResponse.token,
            expires_in = expiresInSeconds
        )
    }
}
