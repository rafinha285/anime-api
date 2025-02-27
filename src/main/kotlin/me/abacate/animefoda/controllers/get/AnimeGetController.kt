package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.errors.animeNotFound
import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class AnimeGetController(private val animeRepository: AnimeRepository) {
    @GetMapping("/g/animes")
    fun getAnimes():List<AnimeModel> = animeRepository.findAll()
    
    @GetMapping("/g/anime/{id}")
    fun getAnime(@PathVariable id:String):AnimeModel {
        return animeRepository.findById(UUID.fromString(id)).orElseThrow {animeNotFound()}
    }
}
