package me.abacate.animefoda.repositories

import me.abacate.animefoda.enums.RoleName
import me.abacate.animefoda.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: RoleName): Optional<Role>
    
    fun existsByName(name: RoleName): Boolean
}
