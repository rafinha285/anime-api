package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.AnimeNotFound
import me.abacate.animefoda.models.*
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.CreatorsRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StudiosRepository
import me.abacate.animefoda.response.AnimeDetailsResponse
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/anime")
class AnimeGetController(
    private val animeRepository: AnimeRepository,
    private val producersRepository: ProducersRepository,
    private val studiosRepository: StudiosRepository,
    private val creatorsRepository: CreatorsRepository
) {
    
    @GetMapping("/all")
    fun getAnimes():ApiResponse<List<AnimeModel>> = ApiResponse(success = true, data = animeRepository.findAll())
    
    @GetMapping("/{id}")
    fun getAnime(@PathVariable id:String):ApiResponse<AnimeModel> {
        val anime = animeRepository.findById(UUID.fromString(id)).orElseThrow { AnimeNotFound(id)}
        return ApiResponse(success = true, data = anime)
    }
    
    @GetMapping("/details/{id}")
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
        val response: AnimeDetailsResponse = AnimeDetailsResponse(producers,creators,studios)
        return ApiResponse<AnimeDetailsResponse>(success = true, data = response)
    }
}
