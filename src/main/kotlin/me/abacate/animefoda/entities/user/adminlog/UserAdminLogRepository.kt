package me.abacate.animefoda.entities.user.adminlog

import org.springframework.data.jpa.repository.JpaRepository

interface UserAdminLogRepository: JpaRepository<UserAdminLog, Long>