package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserWithoutPassword
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepositoryWithoutPassword : JpaRepository<UserWithoutPassword, UUID>
