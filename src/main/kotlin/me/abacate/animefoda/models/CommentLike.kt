package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.embedded.CommentLikeId
import java.time.LocalDateTime


@Entity
@Table(name = "comments_likes", schema = "users")
data class CommentLike(
    @EmbeddedId
    val id: CommentLikeId? = null,
    
    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = true)
    val comment: Comment? = null,
    
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    val user: User? = null,
    
    @Column(name = "date_added", nullable = false)
    val dateAdded: LocalDateTime = LocalDateTime.now()
) {
}