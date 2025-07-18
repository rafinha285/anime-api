package me.abacate.animefoda.entities.user.episodelist

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime

@Entity
@Table(name = "user_episode_list", schema = "users")
open class UserEpisodeList {
    @EmbeddedId
    open var id: UserEpisodeListId? = null
    
    @ColumnDefault("0")
    @Column(name = "dropped_on", nullable = false)
    open var droppedOn: Double? = null
    
    @ColumnDefault("now()")
    @Column(name = "date", nullable = false)
    open var date: OffsetDateTime? = null
}