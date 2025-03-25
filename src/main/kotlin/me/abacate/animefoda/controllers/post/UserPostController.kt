package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.jwt.GenTokenResponse
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.models.UserAnimelistModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.UserAnimelistRepository
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.UUID


@RestController
@RequestMapping("/p/user")
class UserPostController(
    private val userRepository: UserRepository,
    private val animeRepository: AnimeRepository,
    private val animelistRepository: UserAnimelistRepository,
    private val jwtUtil: JWTUtil,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequestEntity: LoginRequestEntity,
        @RequestHeader("User-Agent", required = true) userAgent: String,
    ):ApiResponse<AuthResponse>{
        
        val jwtValue = jwtUtil.generateToken(loginRequestEntity, userAgent)
    
        return ApiResponse(
            data = AuthResponse(
                accessToken = jwtValue.jwt.tokenValue,
                refreshToken = jwtValue.refreshToken,
                expiresIn = jwtValue.expiresIn
            )
        )
    }
    
    @PostMapping("/refresh")
    fun refreshToken(
        @RequestHeader("User-Agent", required = true) userAgent: String,
        @RequestBody refreshToken: RefreshTokenRequestEntity
    ): ApiResponse<AuthResponse>{
        println("jwt refreshToken: "+refreshToken.refreshToken)
        val newToken = jwtUtil.refreshAccessToken(refreshToken = refreshToken.refreshToken, userAgent = userAgent)
        return ApiResponse(
            data = AuthResponse(
                accessToken = newToken.jwt.tokenValue,
                refreshToken = newToken.refreshToken,
                expiresIn = newToken.expiresIn
            )
        )
    }
    
    
}
data class LoginRequestEntity(
    val email: String,
    val password: String,
    val timeZone: String,
    val WebGLVendor: String,
    val WebGLRenderer: String,
)
data class RefreshTokenRequestEntity(
    val refreshToken: String,
)
