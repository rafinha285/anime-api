package me.abacate.animefoda.services

import org.springframework.transaction.annotation.Transactional
import me.abacate.animefoda.models.Anime
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.request.NewAnimeRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnimeService(
    private val animeRepository: AnimeRepository,
    private val animeAssociationService: AnimeAssociationService,
    private val producersService: ProducersService,
    private val creatorsService: CreatorsService,
    private val studiosService: StudiosService
) {
//    @Transactional
//    fun create(anime: Anime): Anime {
//        return animeRepository.save(anime)
//    }
}
