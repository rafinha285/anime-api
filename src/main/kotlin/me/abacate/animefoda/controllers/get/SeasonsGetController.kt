package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.models.SeasonModel
import me.abacate.animefoda.repositories.SeasonRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/season")
class SeasonsGetController (
    private val seasonRepository: SeasonRepository
) {
    @GetMapping("/anime/{animeId}")
    fun getSeasonFromAnime(
        @PathVariable("animeId") animeId: UUID
    ): ApiResponse<List<SeasonModel>>{
        val seasons = seasonRepository.findByAnimeId(animeId)
        return ApiResponse(success = true, data = seasons)
    }
    
    @GetMapping("/{seasonId}")
    fun getSeason(
        @PathVariable("seasonId") seasonId: UUID
    ): ApiResponse<SeasonModel>{
        return ApiResponse(data = seasonRepository.getReferenceById(seasonId))
    }
}
