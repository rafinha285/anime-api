package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.request.NewAnimeRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.AnimeService
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/p/anime/")
class AnimePostController(
    private val animeService: AnimeService,
    private val userService: UserService,
) {
    @PostMapping("/new")
    fun addAnime(
        @RequestBody anime: NewAnimeRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Boolean?> {
        val userId = UUID.fromString(jwt.subject)
        if(!userService.containsRole(userId,RoleName.ROLE_ADMIN) && userService.isSuperUser(userId)) {
            throw UnauthorizedResponse()
        }
        animeService.createAnimeFromRequest(anime)
        return ApiResponse()
    }
}
