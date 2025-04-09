package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.enums.Language
import me.abacate.animefoda.enums.Quality
import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.Anime
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.State
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StateRepository
import me.abacate.animefoda.request.NewAnimeRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.AnimeAssociationService
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
    private val animeRepository: AnimeRepository,
    private val stateRepository: StateRepository,
    private val animeAssociationService: AnimeAssociationService,
    private val producersRepository: ProducersRepository,
) {
    @PostMapping("/new")
    fun addAnime(
        @RequestBody animeRequest: NewAnimeRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Boolean?> {
        val userId = UUID.fromString(jwt.subject)
        if(!userService.containsRole(userId,RoleName.ROLE_ADMIN) && userService.isSuperUser(userId)) {
            throw UnauthorizedResponse()
        }
        
        val state = State(name = animeRequest.state)
        
//        val managedProducers = animeRequest.producers.map { producer ->
//            producersRepository.findByName(producer) ?: producersRepository.save(Producer(name = producer))
//        }.toMutableSet()
//        val creators = animeAssociationService.getCreatorsByIds(animeRequest.creators).toMutableSet()
//        val studios = animeAssociationService.getStudiosByIds(animeRequest.studios).toMutableSet()
        
        
//        val languageEnum = try {
//            Language.valueOf(animeRequest.language)
//        } catch (e: IllegalArgumentException) {
//            return ApiResponse(false, null, "Invalid language value: ${animeRequest.language}")
//        }
        
        
        val anime = Anime(
            name = animeRequest.name,
            name2 = animeRequest.name2,
            description = animeRequest.description,
            genre = animeRequest.gens,
            releaseDate = animeRequest.releasedate,
            quality = animeRequest.quality,
//            language =languageEnum,
            state = state,
        )
        
        
        
        val animeSave = animeRepository.save(anime)
        
//        animeService.createAnimeFromRequest(anime)
        return ApiResponse(message = "Anime ${anime.id} created")
    }
}
