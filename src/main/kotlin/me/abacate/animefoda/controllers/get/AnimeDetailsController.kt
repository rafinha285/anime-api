package me.abacate.animefoda.controllers.get

import ch.qos.logback.core.model.Model
import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.CreatorsModel
import me.abacate.animefoda.models.ProducersModel
import me.abacate.animefoda.models.StudiosModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.CreatorsRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StudiosRepository
import me.abacate.animefoda.response.AnimeDetailsResponse
import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AnimeDetailsController(
    private val animeRepository: AnimeRepository,
    private val producersRepository: ProducersRepository,
    private val creatorsRepository: CreatorsRepository,
    private val studiosRepository: StudiosRepository
) {
    @GetMapping("/g/details/{id}")
    fun getDetail(@PathVariable id: UUID): ApiResponse<AnimeDetailsResponse>{
        val anime = animeRepository.findById(id).orElseThrow()
        { AnimeNotFound(id)}
        val producersId = anime.producers;
        val creatorsId = anime.creators;
        val studiosId = anime.studios;
        
        val producers:MutableList<ProducersModel> = mutableListOf<ProducersModel>()
        val studios:MutableList<StudiosModel> = mutableListOf<StudiosModel>()
        val creators:MutableList<CreatorsModel> = mutableListOf<CreatorsModel>()
        
        for(p in producersId){
            producers.add(producersRepository.getReferenceById(p))
        }
        for(p in studiosId){
            studios.add(studiosRepository.getReferenceById(p))
        }
        for (p in creatorsId){
            creators.add(creatorsRepository.getReferenceById(p))
        }
        val response:AnimeDetailsResponse = AnimeDetailsResponse(producers,creators,studios)
        return ApiResponse<AnimeDetailsResponse>(success = true, data = response)
    }
}
