package me.abacate.animefoda.controllers.delete

import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.repositories.SeasonRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/d/season")
class SeasonDeleteController(
    private val seasonRepository: SeasonRepository,
    private val userService: UserService
) {
    @DeleteMapping("/{aniId}/{id}")
    fun deleteSeason(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable aniId: UUID,
        @PathVariable id: UUID
    ) : ApiResponse<Boolean> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) throw UnauthorizedResponse()
        seasonRepository.deleteById(id)
        return ApiResponse(message = "Season deleted")
    }
}