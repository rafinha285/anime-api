package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.models.Comment
import me.abacate.animefoda.repositories.CommentRepository
import me.abacate.animefoda.request.NewCommentRequest
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/p/comment")
class CommentPostController(
    private val commentRepository: CommentRepository
) {
    @PostMapping("/post")
    fun getComments(
        @RequestBody newCommentRequest: NewCommentRequest,
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<Comment> {
        val parentComment = newCommentRequest.parentId?.let {
            commentRepository.findById(it).orElseThrow {
                BadRequestResponse("Parent comment not found")
            }
        }
        
        val comment = Comment(
            parent = parentComment,
            pageId = newCommentRequest.pageId,
            content = newCommentRequest.content,
            userId = UUID.fromString(jwt.subject)
        );
        return ApiResponse(data = commentRepository.save(comment), message = "Comment posted successfully")
    }
}