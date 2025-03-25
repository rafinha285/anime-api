package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.UserAnimelistModel
import me.abacate.animefoda.repositories.UserAnimelistRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/g/animelist")
class AnimelistGetController(
    private val userAnimelistRepository: UserAnimelistRepository
) {
    @GetMapping("/")
    fun getAnimeListFromToken(
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<List<UserAnimelistModel>> {
        println(jwt.subject)
        val animelist = userAnimelistRepository.findByUserId(UUID.fromString(jwt.subject))
        return ApiResponse(data = animelist)
//        return ApiResponse(success = true)
    }
    @GetMapping("/{id}")
    fun getUserAnimeList(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: UUID,
    ):ApiResponse<UserAnimelistModel>{
        val anime = userAnimelistRepository.findById(id)?: throw AnimeNotFound(id)
        return ApiResponse(data = anime.get())
    }
}
