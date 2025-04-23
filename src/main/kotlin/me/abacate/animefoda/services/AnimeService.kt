package me.abacate.animefoda.services

import org.springframework.transaction.annotation.Transactional
import me.abacate.animefoda.models.Anime
import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.Studio
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.CreatorsRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StudiosRepository
import me.abacate.animefoda.request.AddProducersRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class AnimeService(
    private val animeRepository: AnimeRepository,
    private val animeAssociationService: AnimeAssociationService,
    private val producersRepository: ProducersRepository,
    private val creatorsRepository: CreatorsRepository,
    private val studiosRepository: StudiosRepository,
) {
    //produtores
    fun addProducerToAnime(id: UUID, prod: AddProducersRequest): Anime{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        println(prod)
        val producer: Producer = producersRepository.findByName(prod.name).orElseThrow { RuntimeException("Producer not found!") };
        
        anime.producers.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteProducer(id: UUID, prod:String): Anime {
        val anime = animeRepository.findById(id)
        .orElseThrow { RuntimeException("Anime not found!") }
        val producer = producersRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.producers.remove(producer);
        return animeRepository.save(anime);
    }
    
    //criadores
    fun addCreatorToAnime(id: UUID, crea: AddProducersRequest): Anime{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer: Creator = creatorsRepository.findByName(crea.name).orElseThrow { RuntimeException("Producer not found!") };
        
        anime.creators.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteCreator(id: UUID, prod:String): Anime {
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer = creatorsRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.creators.remove(producer);
        return animeRepository.save(anime);
    }
    
    //studios
    fun addStudioToAnime(id: UUID, stud: AddProducersRequest): Anime{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer: Studio = studiosRepository.findByName(stud.name).orElseThrow { RuntimeException("Producer not found!") };
        
        anime.studios.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteStudio(id: UUID, prod:String): Anime {
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer = studiosRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.studios.remove(producer);
        return animeRepository.save(anime);
    }
    
}
