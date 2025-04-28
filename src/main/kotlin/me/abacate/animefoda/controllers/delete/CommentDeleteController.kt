package me.abacate.animefoda.controllers.delete

import me.abacate.animefoda.repositories.CommentRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
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