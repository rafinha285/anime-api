package me.abacate.animefoda.anime

import jakarta.transaction.Transactional
import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.repositories.StateRepository
import me.abacate.animefoda.request.NewAnimeRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/p/anime/")
class AnimePostController(
    private val userService: UserService,
    private val animeService: AnimeService,
    private val animeRepository: AnimeRepository,
    private val stateRepository: StateRepository,
) {
    @PostMapping("/new")
    fun addAnime(
        @RequestBody animeRequest: NewAnimeRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<AnimeModel> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) {
            throw UnauthorizedResponse()
        }
        
        
//        val creators = animeAssociationService.getCreatorsByIds(animeRequest.creators).toMutableSet()
//        val studios = animeAssociationService.getStudiosByIds(animeRequest.studios).toMutableSet()


//        val languageEnum = try {
//            Language.valueOf(animeRequest.language)
//        } catch (e: IllegalArgumentException) {
//            return ApiResponse(false, null, "Invalid language value: ${animeRequest.language}")
//        }
        
        
        val anime = animeService.insertAnime(animeRequest)
        
//        animeService.createAnimeFromRequest(anime)
        return ApiResponse(data = anime, message = "Anime ${anime.id} created")
    }
    
    @Transactional
    @PostMapping("/update/{id}")
    fun update(
        @RequestBody animeRequest: NewAnimeRequest,
        @PathVariable id: UUID,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<AnimeModel> {
        
        if(!userService.containsRole(UUID.fromString(jwt.subject), RoleName.ROLE_ADMIN)){
            throw UnauthorizedResponse()
        }
        
        val anime = animeRepository.findById(id).orElseThrow {
            throw BadRequestResponse("Anime not found")
        }
        
        anime.name = animeRequest.name
        anime.name2 = animeRequest.name2
        anime.description = animeRequest.description
        anime.genre = animeRequest.genre
        anime.releaseDate = animeRequest.releaseDate
        anime.quality = animeRequest.quality
        anime.language = animeRequest.language
        anime.state = stateRepository.findByName(animeRequest.state).get()
        anime.visible = animeRequest.visible?: false
        anime.weekday = animeRequest.weekday
        
        animeRepository.save(anime)
        
        return ApiResponse(data = anime, message = "Anime ${anime.id} updated")
    }
}