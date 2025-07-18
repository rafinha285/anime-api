package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.anime.AnimeService
import me.abacate.animefoda.entities.season.Season
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EpisodeService(
    private val episodeRepository: EpisodeRepository,
    private val animeService: AnimeService,
) {
    fun getEpisodeVisible(id: UUID): Episode? {
        return episodeRepository.findByIdAndVisible(id)
    }
    
    fun getAllEpisodesVisible(limit: Int? = 10): List<Episode> {
        return episodeRepository.findByVisibleTrue(limit)
    }
    
    fun getAllEpisodes(limit:Int? = 10): List<Episode> {
        return episodeRepository.findByDateAddedAndLimit(limit)
//        return episodeRepository.findAll()
    }
    
    fun getEpisode(id: UUID): Episode? {
        return episodeRepository.findById(id).get()
    }
}