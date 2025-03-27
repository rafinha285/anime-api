package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.UserAnimelistModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.UserAnimelistRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/p/animelist")
class AnimelistPostController(
    private val animelistRepository: UserAnimelistRepository,
    private val animeRepository: AnimeRepository
) {
    @PostMapping("/new/{id}")
    fun addToAnimelist(
        @PathVariable id: String,
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<String?> {
        val anime = animeRepository.findById(UUID.fromString(id)).orElseThrow() {
            throw AnimeNotFound(id)
        }
        val animelistModel = UserAnimelistModel(
            anime.id,
            userId = UUID.fromString(jwt.subject),
        )
        animelistRepository.save(animelistModel)
        return ApiResponse()
    }
}
