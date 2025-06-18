package me.abacate.animefoda.anime

import me.abacate.animefoda.annotation.AdminAction
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.Studio
import me.abacate.animefoda.repositories.CreatorsRepository
import me.abacate.animefoda.repositories.ProducersRepository
import me.abacate.animefoda.repositories.StateRepository
import me.abacate.animefoda.repositories.StudiosRepository
import me.abacate.animefoda.request.AddProducersRequest
import me.abacate.animefoda.request.NewAnimeRequest
import me.abacate.animefoda.services.AnimeAssociationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class AnimeService(
    private val animeRepository: AnimeRepository,
    private val animeAssociationService: AnimeAssociationService,
    private val producersRepository: ProducersRepository,
    private val creatorsRepository: CreatorsRepository,
    private val studiosRepository: StudiosRepository,
    private val stateRepository: StateRepository,
) {
    //produtores
    @AdminAction("ADD PRODUCER TO ANIME id={id} prodName={prod.name}")
    fun addProducerToAnime(id: UUID, prod: AddProducersRequest): AnimeModel{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        println(prod)
        val producer: Producer = producersRepository.findByName(prod.name)
            .orElseGet {
                producersRepository.findByNameIgnoreCase(prod.name) // Busca case-insensitive
                    .orElseGet {
                        producersRepository.save(Producer(name = prod.name))
                    }
            }
        
        anime.producers.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteProducer(id: UUID, prod:String): AnimeModel {
        val anime = animeRepository.findById(id)
        .orElseThrow { RuntimeException("Anime not found!") }
        val producer = producersRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.producers.remove(producer);
        return animeRepository.save(anime);
    }
    
    //criadores
    @AdminAction("ADD CREATOR TO ANIME animeId={id} prodName={crea.name}")
    fun addCreatorToAnime(id: UUID, crea: AddProducersRequest): AnimeModel{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer: Creator = creatorsRepository.findByName(crea.name)
            .orElseGet {
                creatorsRepository.findByNameIgnoreCase(crea.name) // Busca case-insensitive
                    .orElseGet {
                        creatorsRepository.save(Creator(name = crea.name))
                    }
            }
        
        anime.creators.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteCreator(id: UUID, prod:String): AnimeModel {
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer = creatorsRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.creators.remove(producer);
        return animeRepository.save(anime);
    }
    
    //studios
    @AdminAction("ADD STUDIO TO ANIME animeId={id} prodName={stud.name}")
    fun addStudioToAnime(id: UUID, stud: AddProducersRequest): AnimeModel{
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer: Studio = studiosRepository.findByName(stud.name)
            .orElseGet {
                studiosRepository.findByNameIgnoreCase(stud.name)
                    .orElseGet {
                        studiosRepository.save(Studio(name = stud.name))
                    }
            }
        
        anime.studios.add(producer);
        return animeRepository.save(anime);
    }
    fun deleteStudio(id: UUID, prod:String): AnimeModel {
        val anime = animeRepository.findById(id)
            .orElseThrow { RuntimeException("Anime not found!") }
        val producer = studiosRepository.findByName(prod).orElseThrow { RuntimeException("Producer not found!") }
        anime.studios.remove(producer);
        return animeRepository.save(anime);
    }
    
    
    //delete anime
    @AdminAction("DELETE ANIME id={id}")
    fun deleteAnime(id: UUID){
        animeRepository.deleteById(id)
    }
    
    //create anime
    @AdminAction("INSERT ANIME")
    fun insertAnime(animeRequest: NewAnimeRequest): AnimeModel {
        val state = stateRepository.findByName(animeRequest.state).orElseThrow {
            throw BadRequestResponse("State not found")
        }
        
        val managedProducers:MutableSet<Producer> = animeRequest.producers?.map { producer ->
            producersRepository.findByName(producer)
                .orElseGet {
                    producersRepository.findByNameIgnoreCase(producer) // Busca case-insensitive
                        .orElseGet {
                            producersRepository.save(Producer(name = producer))
                        }
                }
        }?.toMutableSet() ?: mutableSetOf()
        val managedCreators:MutableSet<Creator> = animeRequest.creators?.map { producer ->
            creatorsRepository.findByName(producer)
                .orElseGet {
                    creatorsRepository.findByNameIgnoreCase(producer)
                        .orElseGet {
                            creatorsRepository.save(Creator(name = producer))
                        }
                }
        }?.toMutableSet() ?: mutableSetOf()
        val managedStudios:MutableSet<Studio> = animeRequest.studios?.map { producer ->
            studiosRepository.findByName(producer)
                .orElseGet {
                    studiosRepository.findByNameIgnoreCase(producer)
                        .orElseGet {
                            studiosRepository.save(Studio(name = producer))
                        }
                }
        }?.toMutableSet() ?: mutableSetOf()
        
        
        val anime = AnimeModel(
            name = animeRequest.name,
            name2 = animeRequest.name2,
            description = animeRequest.description,
            genre = animeRequest.genre,
            releaseDate = animeRequest.releaseDate,
            quality = animeRequest.quality,
            language =animeRequest.language,
            state = state,
            producers = managedProducers,
            creators = managedCreators,
            studios = managedStudios,
        )
        
        return animeRepository.save(anime)
    }
    
}