package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.AnimeAllModel
import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
class AnimeGetController(private val animeRepository: AnimeRepository) {
    
    @GetMapping("/g/animes")
    fun getAnimes():ApiResponse<List<AnimeAllModel>> = ApiResponse(success = true, data = animeRepository.findAll())
    
    @GetMapping("/g/anime/{id}")
    fun getAnime(@PathVariable id:String):ApiResponse<AnimeAllModel> {
        val anime = animeRepository.findById(UUID.fromString(id)).orElseThrow { AnimeNotFound(id)}
        return ApiResponse(success = true, data = anime)
    }
}
