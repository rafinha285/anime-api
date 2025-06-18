package me.abacate.animefoda.entities.user

import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.entities.role.RoleRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }
    
    fun assignRoleToUser(userId: UUID, roleName: RoleName){
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id $userId was not found") }
        
        val role = roleRepository.findByName(roleName)
            .orElseThrow { IllegalArgumentException("Role with name $roleName was not found") }
        
        user.roles.add(role)
        userRepository.save(user)
    }
    
    fun isAdminAndSuperUser(userId: UUID):Boolean{
        return containsRole(userId, RoleName.ROLE_ADMIN) && isSuperUser(userId);
    }
    
    
    fun containsRole(userId: UUID, roleName: RoleName): Boolean {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id $userId was not found") }
        
        val role = roleRepository.findByName(roleName)
            .orElseThrow { IllegalArgumentException("Role with name $roleName was not found") }
        
        return user.roles.contains(role)
    }
    
    fun isSuperUser(userId: UUID): Boolean {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id $userId was not found") }
        
        return user.superuser
    }
}