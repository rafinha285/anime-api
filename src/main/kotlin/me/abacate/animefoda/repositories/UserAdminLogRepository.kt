package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserAdminLog
import org.springframework.data.jpa.repository.JpaRepository

interface UserAdminLogRepository: JpaRepository<UserAdminLog, Long>