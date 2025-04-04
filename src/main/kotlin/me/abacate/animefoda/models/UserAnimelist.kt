package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.enums.PriorityAnimelist
import me.abacate.animefoda.enums.StateAnimelist
import me.abacate.animefoda.serialized.UserAnimelistId
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_anime_list", schema = "users")
@IdClass(UserAnimelistId::class)
data class UserAnimelist(
    @Id
    @Column(name = "anime_id")
    val animeId: UUID,
    
    @Id
    @Column(name = "user_id")
    val userId: UUID,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "users.status_enum")
    val status: StateAnimelist = StateAnimelist.watching,
    
    @Column(name = "start_date",nullable = true)
    val startDate: LocalDateTime? = LocalDateTime.now(),
    
    @Column(name = "finish_date",nullable = true)
    val finishDate: LocalDateTime? = null,
    
    @Column(name = "rate",nullable = true)
    val rate: Double? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", columnDefinition = "users.priority_value")
    val priority: PriorityAnimelist? = PriorityAnimelist.LOW,
) {
    protected constructor() : this(UUID(0,0),UUID(0,0))
}
