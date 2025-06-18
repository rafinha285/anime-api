package me.abacate.animefoda.entities.commentlike

import jakarta.transaction.Transactional
import me.abacate.animefoda.embedded.CommentLikeId
import me.abacate.animefoda.entities.comment.CommentRepository
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CommentLikeService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val commentLikeRepository: CommentLikeRepository
) {
    @Transactional
    fun toggleLike(commentId: Long, userId: UUID): Boolean {
        val comment = commentRepository.findById(commentId).orElseThrow {
            BadRequestResponse("Comment not found")
        }
        val user = userRepository.findById(userId).orElseThrow {
            BadRequestResponse("User not found")
        }
        
        val likeId = CommentLikeId(userId = userId, commentId = commentId)
        return if (commentLikeRepository.existsById(likeId)) {
            commentLikeRepository.deleteById(likeId) // Método padrão do JpaRepository
            false
        } else {
            commentLikeRepository.save(CommentLike(id = likeId, comment = comment, user = user))
            true
        }
    }
}