package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.repositories.UserAnimelistRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UserNotFound
import me.abacate.animefoda.models.UserModelWithoutPassword
import me.abacate.animefoda.repositories.UserRepositoryWithoutPassword
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/g/user")
class UserGetController(
    private val userAnimelistRepository: UserAnimelistRepository,
    private val userRepositoryWithoutPassword: UserRepositoryWithoutPassword,
    private val userService: UserService,
) {
    @GetMapping("/verify")
    fun verify(
    ): ApiResponse<String?> {
        return try{
            ApiResponse(success = true)
        }catch(e: Exception){
            ApiResponse(success = false, message = e.localizedMessage)
        }
    }
    
    @GetMapping("/check/role/{roleName}")
    fun userIsAdm(
        @PathVariable("roleName") roleName: String,
        @AuthenticationPrincipal jwt: Jwt,
    ):ApiResponse<Boolean>{
        try{
            val role = RoleName.valueOf(roleName)
            val isAdm = userService.containsRole(UUID.fromString(jwt.subject),role)
            return ApiResponse(data = isAdm)
        }catch(e: Exception){
            throw BadRequestResponse(reason = e.localizedMessage)
        }
    }
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserModelWithoutPassword> {
        val user = userRepositoryWithoutPassword.findById(id).orElseThrow() { UserNotFound(id)}
        return ApiResponse(success = true, data = user)
    }
    
    @GetMapping("/")
    fun getUserFromToken(
        @AuthenticationPrincipal jwt:Jwt
    ): ApiResponse<UserModelWithoutPassword> {
        val user = userRepositoryWithoutPassword.findById(UUID.fromString(jwt.subject)).orElseThrow()
        return ApiResponse(data = user)
    }
}
