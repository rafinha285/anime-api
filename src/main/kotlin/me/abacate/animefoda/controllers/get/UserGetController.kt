package me.abacate.animefoda.controllers.get

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.errors.UserNotFound
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.models.UserModelWithoutPassword
import me.abacate.animefoda.repositories.UserRepositoryWithoutPassword
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/g/user")
class UserGetController(
    private val userRepositoryWithoutPassword: UserRepositoryWithoutPassword,
    private val jwtUtil: JWTUtil
) {
    @GetMapping("/verify")
    fun verify(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @CookieValue(name = "token") token: String
    ): ApiResponse<String?> {
        try{
            jwtUtil.checkToken(token, request, response);
            return ApiResponse(success = true)
        }catch(e: Exception){
            throw e
        }
    }
    
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserModelWithoutPassword> {
        val user = userRepositoryWithoutPassword.findById(id).orElseThrow() { UserNotFound(id)}
        return ApiResponse(success = true, data = user)
    }
}
