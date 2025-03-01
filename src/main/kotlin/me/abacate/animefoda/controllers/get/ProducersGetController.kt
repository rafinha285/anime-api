package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.ProducersModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
class ProducersGetController(
    private val repository: ProducersRepository,
    private val animeRepository: AnimeRepository
) {
    
    @GetMapping("/g/producers/anime/{id}")
    fun getProducersFromAnime(@PathVariable id:String): ApiResponse<List<ProducersModel>> {
        val anime = animeRepository.findById(UUID.fromString(id))
            .orElseThrow { AnimeNotFound("") }
        val producers = mutableListOf<ProducersModel>()
        for (p in anime.producers) {
            val producer = repository.getReferenceById(p)
            producers.add(producer)
        }
        return ApiResponse(success = true,data = producers)
    }
    
    @GetMapping("/g/producers/{id}")
    fun getProducers(@PathVariable id: String): ApiResponse<ProducersModel> {
        return ApiResponse(success = true,data = repository.getReferenceById(UUID.fromString(id)))
    }
    
}
