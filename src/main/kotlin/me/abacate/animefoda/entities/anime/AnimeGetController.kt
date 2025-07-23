package me.abacate.animefoda.anime

import me.abacate.animefoda.entities.anime.AnimeDTO
import me.abacate.animefoda.entities.anime.AnimeRepository
import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.entities.user.UserService
import me.abacate.animefoda.response.AdminAccess
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
        @RequestParam(defaultValue = "true") summary: Boolean,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ApiResponse<List<Any>> {
        val isAdmin = jwt != null && userService.containsRole(jwt.subject,RoleName.ROLE_ADMIN)
        val animes = if (isAdmin) {
            animeRepository.findAll()
        }else{
            animeRepository.findByVisibleTrue()
        }
        
        val data = if(summary){
            animes.map { it.toSummaryDTO() }
        }else{
            animes.map { it.toDTO() }
        }
        return ApiResponse(message = AdminAccess.adminAccess(isAdmin), data = data)
//        if(jwt != null) {
//            val isAdmin = userService.containsRole(jwt?.subject!!, RoleName.ROLE_ADMIN)
//            return if (isAdmin)
//                ApiResponse(message = "Admin access", data = animeRepository.findAll().map { it.toSummaryDTO() })
//            else
//                ApiResponse(data = animeRepository.findByVisibleTrue().map{it.toDTO()})
//        }else{
//            return ApiResponse(data = animeRepository.findByVisibleTrue().map{it.toDTO()})
//       }
    }
    
    @GetMapping("/{id}")
    @Cacheable(cacheNames = ["animeById"], key = "#id")
    fun getAnime(
        @PathVariable id:String,
    ): ApiResponse<AnimeDTO> {
        val anime = animeRepository.findById(UUID.fromString(id)).orElseThrow { AnimeNotFound(id) }
        return ApiResponse(success = true, data = anime.toDTO())
    }
    
    @GetMapping("/genre/{gen}")
    fun getByGen(
        @PathVariable gen: String,
        @AuthenticationPrincipal jwt: Jwt?,
        @RequestParam(defaultValue = "true") summary: Boolean,
    ): ApiResponse<List<Any>>{
        val isAdmin = jwt != null && userService.containsRole(jwt.subject!!, RoleName.ROLE_ADMIN)
        val animes = if(isAdmin){
            animeRepository.findByGenreContains(gen)
        }else{
            animeRepository.findByGenreContainsAndVisible(gen, true)
        }
        
        val data = if(summary){
            animes.map { it.toSummaryDTO() }
        }else{
            animes.map { it.toDTO() }
        }
        return ApiResponse(message = AdminAccess.adminAccess(isAdmin), data = data)
//        if(jwt != null) {
//            val isAdmin = userService.containsRole(jwt!!.subject, RoleName.ROLE_ADMIN)
//            return if (isAdmin) {
//                ApiResponse(data= animeRepository.findByGenreContains(gen).map { it.toDTO() }, message = "Admin access")
//            }else{
//                ApiResponse(data = animeRepository.findByGenreContainsAndVisible(gen, true).map { it.toDTO() })
//            }
//        }
//        return ApiResponse(data = animeRepository.findByGenreContainsAndVisible(gen, true).map { it.toDTO() })
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