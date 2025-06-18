package me.abacate.animefoda.entities.comment

import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.CommentResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/comment")
class CommentGetController(
    private val commentRepository: CommentRepository,
    private val commentService: CommentService,
) {
    @GetMapping("/page/{pageId}")
    fun getCommentsByPage(@PathVariable("pageId") pageId: UUID): ApiResponse<List<CommentResponse>> {
        return ApiResponse(data = commentService.getCommentHierarchy(pageId, null))
    }
    
    @GetMapping("/user/{user}")
    fun getCommentsByUser(@PathVariable("user") user: UUID): ApiResponse<List<CommentResponse>> {
        return ApiResponse(data = commentService.getCommentHierarchy(userId = user, pageId = null))
    }
}