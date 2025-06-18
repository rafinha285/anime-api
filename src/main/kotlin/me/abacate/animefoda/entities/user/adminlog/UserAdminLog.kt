package me.abacate.animefoda.entities.user.adminlog

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.abacate.animefoda.entities.user.User
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "user_admin_log", schema = "users")
data class UserAdminLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,
    
    @Column(name = "command", nullable = false)
    val command: String,
    
    @Column(name = "session_id", nullable = false)
    val sessionId: UUID,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @Column(name = "date", nullable = false)
    val date: ZonedDateTime = ZonedDateTime.now(),
)