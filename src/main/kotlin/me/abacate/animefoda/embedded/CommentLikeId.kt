package me.abacate.animefoda.embedded

import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID

@Embeddable
data class CommentLikeId(
    @Column(name = "user_id")
    val userId: UUID = UUID(0, 0),
    
    @Column(name = "comment_id")
    val commentId: Long = 0
): Serializable