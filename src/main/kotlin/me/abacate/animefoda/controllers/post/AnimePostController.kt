package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.Anime
import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.Studio
import me.abacate.animefoda.repositories.*
import me.abacate.animefoda.request.NewAnimeRequest
import me.abacate.animefoda.response.ApiResponse
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
    private val userService: UserService,
    private val animeRepository: AnimeRepository,
    private val stateRepository: StateRepository,
    private val producersRepository: ProducersRepository,
    private val creatorsRepository: CreatorsRepository,
    private val studiosRepository: StudiosRepository,
) {
    @PostMapping("/new")
    fun addAnime(
        @RequestBody animeRequest: NewAnimeRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Anime> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) {
            throw UnauthorizedResponse()
        }
        
        val state = stateRepository.findByName(animeRequest.state).orElseThrow {
            throw BadRequestResponse("State not found")
        }
        
        val managedProducers:MutableSet<Producer> = animeRequest.producers.map { producer ->
            producersRepository.findByName(producer).get() ?: producersRepository.save(Producer(name = producer))
        }.toMutableSet()
        val managedCreators:MutableSet<Creator> = animeRequest.creators.map { producer ->
            creatorsRepository.findByName(producer).get() ?: creatorsRepository.save(Creator(name = producer))
        }.toMutableSet()
        val managedStudios:MutableSet<Studio> = animeRequest.studios.map { producer ->
            studiosRepository.findByName(producer).get() ?: studiosRepository.save(Studio(name = producer))
        }.toMutableSet()
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
            releaseDate = animeRequest.releaseDate,
            quality = animeRequest.quality,
            language =animeRequest.language,
            state = state,
            producers = managedProducers,
            creators = managedCreators,
            studios = managedStudios,
        )
        
        
        
        animeRepository.save(anime)
        
//        animeService.createAnimeFromRequest(anime)
        return ApiResponse(data = anime,message = "Anime ${anime.id} created")
    }
}
