package me.abacate.animefoda.services

import org.springframework.transaction.annotation.Transactional
import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.Studio
import me.abacate.animefoda.anime.AnimeRepository
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
    @Transactional(readOnly = true)
    fun getCreatorsByIds(names: List<String>): Set<Creator> {
        val existingCreators = creatorsRepository.findByNameIn(names)
        val existingNames = existingCreators.map { it.name }.toSet()
        
        val newCreators = names
            .filterNot { it in existingNames }
            .map { Creator(name = it) }
        
        if(newCreators.isNotEmpty()){
            creatorsRepository.saveAll(newCreators)
        }
        return (existingCreators + newCreators).toSet()
    }
    
    @Transactional(readOnly = true)
    fun getProducersByIds(names: List<String>): Set<Producer> {
        val existingProducers = producersRepository.findByNameIn(names)
        val existingNames = existingProducers.map { it.name }.toSet()
        
        val newProducers = names
            .filterNot { it in existingNames }
            .map { Producer(name = it) }
        
        if(newProducers.isNotEmpty()) {
            producersRepository.saveAll(newProducers)
        }
        
        return (existingProducers + newProducers).toSet()
    }
    
    @Transactional(readOnly = true)
    fun getStudiosByIds(names: List<String>): Set<Studio> {
        val existingStudios = studiosRepository.findByNameIn(names)
        val existingNames = existingStudios.map { it.name }.toSet()
        
        val newStudios = names
            .filterNot { it in existingNames }
            .map { Studio(name = it) }
        if(newStudios.isNotEmpty()) {
            studiosRepository.saveAll(newStudios)
        }
        return (existingStudios + newStudios).toSet()
    }
}
