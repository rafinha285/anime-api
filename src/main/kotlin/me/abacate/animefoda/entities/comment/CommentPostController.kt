package me.abacate.animefoda.entities.comment

import jakarta.transaction.Transactional
import me.abacate.animefoda.errors.BadRequestResponse
import me.abacate.animefoda.entities.user.UserRepository
import me.abacate.animefoda.request.LikeCommentRequest
import me.abacate.animefoda.request.NewCommentRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.entities.commentlike.CommentLikeService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/p/comment")
class CommentPostController(
    private val commentRepository: CommentRepository,
    private val commentLikeService: CommentLikeService,
    private val userRepository: UserRepository,
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
    
    @PostMapping("/like")
    @Transactional
    fun likeComment(
        @RequestBody newLikeCommentRequest: LikeCommentRequest,
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<Boolean> {
        val userId = UUID.fromString(jwt.subject)
        val toggle = commentLikeService.toggleLike(newLikeCommentRequest.commentId, userId)
        return ApiResponse(data = toggle, message = if (toggle) "Comment liked" else "Comment Disliked")
    }
}