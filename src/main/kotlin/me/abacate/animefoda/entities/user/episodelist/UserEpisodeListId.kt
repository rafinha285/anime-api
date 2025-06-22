package me.abacate.animefoda.entities.user.episodelist

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class UserEpisodeListId : Serializable {
    @Column(name = "episode_id", nullable = false)
    open var episodeId: UUID? = null
    
    @Column(name = "season_id", nullable = false)
    open var seasonId: UUID? = null
    
    @Column(name = "anime_id", nullable = false)
    open var animeId: UUID? = null
    
    @Column(name = "user_id", nullable = false)
    open var userId: UUID? = null
    override fun hashCode(): Int = Objects.hash(episodeId, seasonId, animeId, userId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        
        other as UserEpisodeListId
        
        return episodeId == other.episodeId &&
                seasonId == other.seasonId &&
                animeId == other.animeId &&
                userId == other.userId
    }
    
    companion object {
        private const val serialVersionUID = 0L
    }
}