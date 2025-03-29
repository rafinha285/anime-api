package me.abacate.animefoda.services

import jakarta.transaction.Transactional
import me.abacate.animefoda.models.AnimeModel
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.request.NewAnimeRequest
import org.springframework.stereotype.Service

@Service
class AnimeService(
    private val animeRepository: AnimeRepository,
    private val animeAssociationService: AnimeAssociationService,
    private val producersService: ProducersService,
    private val creatorsService: CreatorsService,
    private val studiosService: StudiosService
) {
    @Transactional
    fun create(anime: AnimeModel): AnimeModel {
        return animeRepository.save(anime)
    }
    @Transactional
    fun createAnimeFromRequest(newAnimeRequest: NewAnimeRequest){

        val producers = producersService.getIdsFromNames(newAnimeRequest.producers)
        val creators = creatorsService.getIdsFromNames(newAnimeRequest.creators)
        val studios = studiosService.getIdsFromNames(newAnimeRequest.studios)

        var anime = AnimeModel(
            name = newAnimeRequest.name,
            name2 = newAnimeRequest.name2,
            description = newAnimeRequest.description,
            genre = newAnimeRequest.gens,
            releaseDate = newAnimeRequest.releasedate,
            quality = newAnimeRequest.quality,
            state =  newAnimeRequest.state,
            language =  newAnimeRequest.language,
        )
        
        anime = animeAssociationService.addCreatorToAnime(anime, creatorsId = creators)
        anime = animeAssociationService.addStudioToAnime(anime, studiosId = studios)
        anime = animeAssociationService.addProducerToAnime(anime, producersId = producers)
        
        animeRepository.save(anime)
    }
}
