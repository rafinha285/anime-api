package me.abacate.animefoda.loader

import me.abacate.animefoda.entities.state.StateName
import me.abacate.animefoda.entities.state.State
import me.abacate.animefoda.entities.state.StateRepository
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