package me.abacate.animefoda.entities.user

import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.errors.UserNotFound
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.UserResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/user")
class UserGetController(
    private val userService: UserService,
    private val userRepository: UserRepository,
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
    ): ApiResponse<Boolean> {
        try{
            val role = RoleName.valueOf(roleName)
            val isAdm = userService.containsRole(UUID.fromString(jwt.subject),role)
            return ApiResponse(data = isAdm)
        }catch(e: Exception){
            throw BadRequestResponse(reason = e.localizedMessage)
        }
    }
    
    @GetMapping("/all")
    fun allUsers(
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<List<UserResponse>> {
        if(!userService.containsRole(UUID.fromString(jwt.subject), RoleName.ROLE_ADMIN))
            throw UnauthorizedResponse();
        val users = userRepository.findAll().toList().map{it.toResponse()}
        return ApiResponse(success = true, message = "Admin action", data = users)
    }
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserResponse> {
        val user = userRepository.findById(id).orElseThrow { UserNotFound(id) }
        return ApiResponse(success = true, data = user.toResponse())
    }
    
    @GetMapping("/")
    fun getUserFromToken(
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<UserResponse> {
        val user = userRepository.findById(UUID.fromString(jwt.subject)).orElseThrow {
            BadRequestResponse(reason = "User not found")
        }
        return ApiResponse(data = user.toResponse())
    }
}