package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.errors.UserNotFound
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.models.UserSession
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.repositories.UserRepositoryWithoutPassword
import me.abacate.animefoda.repositories.UserSessionRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequestEntity(
    val email: String,
    val password: String,
    val timeZone: String,
    val WebGLVendor: String,
    val WebGLRenderer: String,
)

@RestController
@RequestMapping("/p/user/")
class UserPostController(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil
) {
    @PostMapping("/login/")
    fun login(@RequestBody userSession: LoginRequestEntity): ApiResponse<AuthResponse> {
//        val user = userRepository.getReferenceById(userSession.userId)
//        userSessionRepository.save(userSession)
//        val userToken = UserSession
        val find = userRepository.findByEmailAndPassword(email = userSession.email, password = userSession.password)
        if(find == null){
            throw UnauthorizedResponse()
        }
        println(find.id)
        val jwtResponse = jwtUtil.generateToken(userSession)
        return ApiResponse(data = jwtResponse)
    }
}
