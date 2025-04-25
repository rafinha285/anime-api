package me.abacate.animefoda.loader

import me.abacate.animefoda.enums.StateName
import me.abacate.animefoda.models.State
import me.abacate.animefoda.repositories.StateRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class StateDataLoader(
    private val stateRepository: StateRepository
) {
    @Bean
    fun loadStateData() = ApplicationRunner{
        StateName.entries.forEach { stateName ->
            if(!stateRepository.existsByName(stateName)){
                stateRepository.save(State(name = stateName))
            }
        }
    }
}