package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.animeNotFound
import me.abacate.animefoda.models.ProducersModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.ProducersRepository
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
    
    @GetMapping("/producers/g/anime/{id}")
    fun getProducersFromAnime(@PathVariable id:String):List<ProducersModel>{
        val anime = animeRepository.findById(UUID.fromString(id))
            .orElseThrow { animeNotFound() }
        val producers = mutableListOf<ProducersModel>()
        println(anime.producers)
        for (p in anime.producers) {
            println(p)
            val producer = repository.getReferenceById(p)
            println(producer)
            producers.add(producer)
        }
        return producers
    }
    
    @GetMapping("/producers/g/{id}")
    fun getProducers(@PathVariable id: String): ProducersModel {
        return repository.getReferenceById(UUID.fromString(id))
    }
    
}
