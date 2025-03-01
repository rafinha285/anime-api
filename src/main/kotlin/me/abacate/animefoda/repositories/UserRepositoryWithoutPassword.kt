package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserModelWithoutPassword
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepositoryWithoutPassword : JpaRepository<UserModelWithoutPassword, UUID>
