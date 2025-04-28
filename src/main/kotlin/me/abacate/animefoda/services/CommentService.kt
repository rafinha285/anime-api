package me.abacate.animefoda.services

import jakarta.transaction.Transactional
import me.abacate.animefoda.embedded.CommentLikeId
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.models.Comment
import me.abacate.animefoda.models.CommentLike
import me.abacate.animefoda.repositories.CommentLikeRepository
import me.abacate.animefoda.repositories.CommentRepository
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.CommentResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val userRepository: UserRepository,
) {
    fun getCommentHierarchy(pageId: UUID?, userId: UUID?): List<CommentResponse> {
        val allComments = commentRepository.findCommentsWithFilters(pageId, userId)
        
        return buildHierarchy(allComments)
    }
    
    private fun buildHierarchy(comments: List<Comment>): List<CommentResponse> {
        val commentMap = comments.associateBy { it.id }
        
        return comments
            .filter { it.parent == null }
            .map { convertToResponse(it, commentMap) }
    }
    
    private fun convertToResponse(comment: Comment, commentMap: Map<Int?,Comment>): CommentResponse{
        return CommentResponse(
            id = comment.id!!,
            parentId = comment.parent?.id,
            pageId = comment.pageId!!,
            userId = comment.userId!!,
            content = comment.content,
            createdAt = comment.createdAt,
            children = comment.children
                .sortedByDescending { it.createdAt }
                .map { convertToResponse(commentMap[it.id]!!, commentMap) },
            likes = comment.totalLikes,
        )
    }
}