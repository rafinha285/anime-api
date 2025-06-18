package me.abacate.animefoda.loader

import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.entities.role.Role
import me.abacate.animefoda.entities.role.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class InitDataLoader(
    private val roleRepository: RoleRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        RoleName.values().forEach {rolename ->
            if(!roleRepository.existsByName(rolename)){
                roleRepository.save(Role(name = rolename))
            }
        }
    }
}
