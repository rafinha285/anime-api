package me.abacate.animefoda.entities.user

import me.abacate.animefoda.request.LoginRequest
import me.abacate.animefoda.request.NewUserRequest
import me.abacate.animefoda.request.RefreshTokenRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import me.abacate.animefoda.response.UserResponse
import me.abacate.animefoda.services.JWTService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/p/user")
class UserPostController(
    private val userRepository: UserRepository,
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
    
    @PostMapping("/new")
    fun newUser(
        @RequestBody newUser: NewUserRequest
    ): ApiResponse<UserResponse> {
        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(newUser.password, salt)
        val user = User(
            username = newUser.username,
            name = newUser.name,
            surname = newUser.surname,
            email = newUser.email,
            birthdate = newUser.birthdate,
            password = hashedPassword,
            salt = salt,
        )
        userRepository.save(user)
        val userResponse = user.id?.let { userRepository.findById(it) }?.get()
        return ApiResponse(data = userResponse?.toResponse())
    }
}