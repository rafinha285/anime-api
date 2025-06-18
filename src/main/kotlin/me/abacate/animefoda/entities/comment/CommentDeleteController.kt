package me.abacate.animefoda.entities.comment

import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/d/comment")
class CommentDeleteController(private val commentRepository: CommentRepository) {
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ApiResponse<Boolean> {
        commentRepository.deleteById(commentId)
        return ApiResponse(data = true, message = "Comment $commentId deleted")
    }
}