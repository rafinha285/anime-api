package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AnimePostController(
    private val animeRepository: AnimeRepository
) {
    @PostMapping("/p/new/anime")
    fun addAnime(@RequestBody anime: AnimeModel) {
    
    }
}
