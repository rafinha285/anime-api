package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.services.JWTService
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.UserAnimelistRepository
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.request.LoginRequest
import me.abacate.animefoda.request.RefreshTokenRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/p/user")
class UserPostController(
    private val userRepository: UserRepository,
    private val animeRepository: AnimeRepository,
    private val animelistRepository: UserAnimelistRepository,
    private val jwtService: JWTService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        @RequestHeader("User-Agent", required = true) userAgent: String,
    ): ApiResponse<AuthResponse> {
        
        val jwtValue = jwtService.generateToken(loginRequest, userAgent)
        
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
        @RequestBody refreshToken: RefreshTokenRequest
    ): ApiResponse<AuthResponse> {
        println("jwt refreshToken: " + refreshToken.refreshToken)
        val newToken = jwtService.refreshAccessToken(refreshToken = refreshToken.refreshToken, userAgent = userAgent)
        return ApiResponse(
            data = AuthResponse(
                accessToken = newToken.jwt.tokenValue,
                refreshToken = newToken.refreshToken,
                expiresIn = newToken.expiresIn
            )
        )
    }
    
    
}
