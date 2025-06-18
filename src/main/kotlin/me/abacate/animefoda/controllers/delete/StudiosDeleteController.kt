package me.abacate.animefoda.controllers.delete

import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.anime.AnimeService
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/d/studios/")
class StudiosDeleteController(
    private val animeService: AnimeService,
    private val userService: UserService
) {
    @DeleteMapping("/anime/{id}/{prodName}")
    fun delete(
        @PathVariable id: UUID,
        @PathVariable prodName: String,
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<Boolean?>{
        if(!userService.containsRole(UUID.fromString(jwt.subject), RoleName.ROLE_ADMIN)){
            throw UnauthorizedResponse()
        }
        animeService.deleteStudio(id,prodName);
        return ApiResponse(message = "Creator deleted!")
    }
}