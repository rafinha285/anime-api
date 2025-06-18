package me.abacate.animefoda.entities.commentlike

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class CommentLikeId(
    @Column(name = "user_id")
    val userId: UUID = UUID(0, 0),
    
    @Column(name = "comment_id")
    val commentId: Long = 0
): Serializable