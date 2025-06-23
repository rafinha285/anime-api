package me.abacate.animefoda.anime

import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.entities.creator.CreatorsRepository
import me.abacate.animefoda.entities.producer.ProducersRepository
import me.abacate.animefoda.entities.studio.StudiosRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.entities.user.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/anime")
class AnimeGetController(
    private val animeRepository: AnimeRepository,
    private val userService: UserService
) {
    
    @GetMapping("/all")
    fun getAnimes(
        @AuthenticationPrincipal jwt: Jwt?,
    ): ApiResponse<List<Anime>> {
        val isAdmin = jwt?.subject?.let { subject ->
            try {
                userService.containsRole(UUID.fromString(subject), RoleName.ROLE_ADMIN)
            } catch (e: IllegalArgumentException) {
                false
            }
        } ?: false
        return if (isAdmin)
            ApiResponse(message = "Admin access", data = animeRepository.findAll())
        else
            ApiResponse(data = animeRepository.findByVisibleTrue())
    }
    
    @GetMapping("/{id}")
    fun getAnime(@PathVariable id:String): ApiResponse<Anime> {
        val anime = animeRepository.findById(UUID.fromString(id)).orElseThrow { AnimeNotFound(id) }
        return ApiResponse(success = true, data = anime)
    }
    
//    @GetMapping("/details/{id}")
//    fun getDetail(@PathVariable id: UUID): ApiResponse<AnimeDetailsResponse>{
//        val anime = animeRepository.findById(id).orElseThrow()
//        { AnimeNotFound(id)}
//        val producersId = anime.producers;
//        val creatorsId = anime.creators;
//        val studiosId = anime.studios;
//
//        val producers:MutableList<Producer> = mutableListOf<Producer>()
//        val studios:MutableList<Studio> = mutableListOf<Studio>()
//        val creators:MutableList<Creator> = mutableListOf<Creator>()
//
//        for(p in producersId){
//            producers.add(producersRepository.getReferenceById(p))
//        }
//        for(p in studiosId){
//            studios.add(studiosRepository.getReferenceById(p))
//        }
//        for (p in creatorsId){
//            creators.add(creatorsRepository.getReferenceById(p))
//        }
//        val response: AnimeDetailsResponse = AnimeDetailsResponse(producers,creators,studios)
//        return ApiResponse<AnimeDetailsResponse>(success = true, data = response)
//    }
}