package me.abacate.animefoda.entities.commentlike

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import me.abacate.animefoda.embedded.CommentLikeId
import me.abacate.animefoda.entities.comment.Comment
import me.abacate.animefoda.models.User
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