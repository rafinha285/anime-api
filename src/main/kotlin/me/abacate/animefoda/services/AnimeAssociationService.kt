package me.abacate.animefoda.services

import jakarta.transaction.Transactional
import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.CreatorsRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StudiosRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnimeAssociationService(
    private val animeRepository: AnimeRepository,
    private val creatorsRepository: CreatorsRepository,
    private val studiosRepository: StudiosRepository,
    private val producersRepository: ProducersRepository
) {
    @Transactional
    fun addCreatorToAnime(anime:AnimeModel, creatorsId:List<UUID>): AnimeModel {
        for(creatorId in creatorsId) {
            val creator = creatorsRepository.findById(creatorId).orElseThrow()
            anime.creators.add(creator)
        }
        return anime
    }
    @Transactional
    fun addProducerToAnime(anime: AnimeModel, producersId: List<UUID>): AnimeModel {
        for(producerId in producersId) {
            val producer = producersRepository.findById(producerId).orElseThrow()
            anime.producers.add(producer)
        }
        return anime
    }
    
    @Transactional
    fun addStudioToAnime(anime: AnimeModel, studiosId: List<UUID>): AnimeModel {
        for(studioId in studiosId) {
            val studio = studiosRepository.findById(studioId).orElseThrow()
            anime.studios.add(studio)
        }
        return anime
    }
}
