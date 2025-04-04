package me.abacate.animefoda.loader

import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.enums.StateName
import me.abacate.animefoda.models.Role
import me.abacate.animefoda.models.State
import me.abacate.animefoda.repositories.RoleRepository
import me.abacate.animefoda.repositories.StateRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class InitDataLoader(
    private val roleRepository: RoleRepository,
    private val stateRepository: StateRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        RoleName.values().forEach {rolename ->
            if(!roleRepository.existsByName(rolename)){
                roleRepository.save(Role(name = rolename))
            }
        }
    }
}
