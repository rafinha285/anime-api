package me.abacate.animefoda.entities.user.animelist

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.abacate.animefoda.anime.Anime
import me.abacate.animefoda.entities.user.animelist.UserAnimelistId
import me.abacate.animefoda.enums.PriorityAnimelist
import me.abacate.animefoda.enums.StateAnimelist
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_anime_list", schema = "users")
@IdClass(UserAnimelistId::class)
data class UserAnimelist(
    @Id
    @Column(name = "anime_id", nullable = false)
    val animeId: UUID? = null,
    
    @Id
    @Column(name = "user_id", nullable = false)
    val userId: UUID? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", insertable = false, updatable = false)
    val anime: Anime? = null,
    
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
    //UUID(0, 0)
    protected constructor() : this(UUID(0, 0),UUID(0,0))
}