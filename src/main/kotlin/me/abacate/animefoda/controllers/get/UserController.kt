package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.UserNotFound
import me.abacate.animefoda.models.UserModel
import me.abacate.animefoda.models.UserModelWithoutPassword
import me.abacate.animefoda.repositories.UserRepositoryWithoutPassword
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/g/user")
class UserController(private val userRepositoryWithoutPassword: UserRepositoryWithoutPassword) {
    @GetMapping("/verify")
    fun verify(): ApiResponse<String?> {
        return ApiResponse(success = true)
    }
    
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): ApiResponse<UserModelWithoutPassword> {
        val user = userRepositoryWithoutPassword.findById(id).orElseThrow() { UserNotFound(id)}
        return ApiResponse(success = true, data = user)
    }
}
