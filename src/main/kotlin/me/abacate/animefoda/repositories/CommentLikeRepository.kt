package me.abacate.animefoda.repositories

import me.abacate.animefoda.embedded.CommentLikeId
import me.abacate.animefoda.models.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, CommentLikeId> {
    fun deleteCommentLikesById(commentLikeId: CommentLikeId)
}