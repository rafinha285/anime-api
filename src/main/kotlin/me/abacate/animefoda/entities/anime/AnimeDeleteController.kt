package me.abacate.animefoda.entities.anime

import me.abacate.animefoda.anime.AnimeService
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.entities.user.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/d/anime")
class AnimeDeleteController(
    private val animeService: AnimeService,
    private val userService: UserService,
) {
    @DeleteMapping("/{id}")
    fun deleteAnime(
        @PathVariable id: UUID,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Boolean?> {
        
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) throw UnauthorizedResponse()
        
        animeService.deleteAnime(id);
        
        return ApiResponse(message = "Anime deleted ${id}")
    }
}