package me.abacate.animefoda.controllers.post

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import org.springframework.web.bind.annotation.*

data class LoginRequestEntity(
    val email: String,
    val password: String,
    val timeZone: String,
    val WebGLVendor: String,
    val WebGLRenderer: String,
)

@RestController
@RequestMapping("/p/user")
class UserPostController(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil
) {
    @PostMapping("/login")
    fun login(
        @RequestBody userSession: LoginRequestEntity,
        @RequestHeader(name = "User-Agent") userAgent: String,
        response: HttpServletResponse
    ): ApiResponse<AuthResponse> {
        val find = userRepository.findByEmailAndPassword(email = userSession.email, password = userSession.password)
        if(find == null){
            throw UnauthorizedResponse()
        }
        println(find.id)
        if(userAgent == ""){
            throw BadRequestResponse()
        }
        val jwtResponse = jwtUtil.generateToken(userSession,userAgent)
        val cookie = Cookie("token", jwtResponse.token)
        response.addCookie(cookie)
        return ApiResponse(data = jwtResponse)
    }
}
