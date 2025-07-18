package me.abacate.animefoda.entities.user.session

import jakarta.persistence.*
import me.abacate.animefoda.entities.user.User
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users_sessions", schema = "users")
data class UserSession(
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id", nullable = false)
    val sessionId: UUID = UUID.randomUUID(),
    
//    @Column(name = "user_id", nullable = false)
//    val userId: UUID = UUID.randomUUID(),
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "users",
//        schema = "users",
//        joinColumns = [JoinColumn(name = "user_id")],
//        inverseJoinColumns = [JoinColumn(name = "session_id")]
//    )
//    val user: User? = null,
    @ManyToOne(fetch = FetchType.LAZY)  // Changed to LAZY for performance
    @JoinColumn(name = "user_id", referencedColumnName = "_id")  // Correct join configuration
    val user: User? = null,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "expires_at", nullable = false)
    val expiresAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "user_agent", nullable = false)
    val userAgent: String = "",
    
    @Column(name = "web_gl_vendor", nullable = false)
    val webGlVendor: String = "",
    
    @Column(name = "web_gl_renderer", nullable = false)
    val webGlRenderer: String = "",
    
    @Column(name = "enabled", nullable = false)
    val enabled: Boolean = true,
    
    @Column(name = "time_zone", nullable = false)
    val timeZone: String = ""
)