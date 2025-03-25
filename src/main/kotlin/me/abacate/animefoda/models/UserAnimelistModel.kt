package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.enums.PriorityAnimelist
import me.abacate.animefoda.enums.StateAnimelist
import me.abacate.animefoda.serialized.UserAnimeListId
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_anime_list", schema = "users")
data class UserAnimelistModel(
    @Id
    @Column(name = "anime_id")
    val animeId: UUID,
    
    @Column(name = "user_id")
    val userId: UUID,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: StateAnimelist = StateAnimelist.watching,
    
    @Column(name = "start_date",nullable = true)
    val startDate: LocalDateTime? = LocalDateTime.now(),
    
    @Column(name = "finish_date",nullable = true)
    val finishDate: LocalDateTime? = null,
    
    @Column(name = "rate",nullable = true)
    val rate: Double? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    val priority: PriorityAnimelist = PriorityAnimelist.LOW,
)
