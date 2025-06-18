package me.abacate.animefoda.entities.commentlike

import me.abacate.animefoda.entities.commentlike.CommentLikeId
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, CommentLikeId> {
    fun deleteCommentLikesById(commentLikeId: CommentLikeId)
}