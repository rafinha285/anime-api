package me.abacate.animefoda.controllers.get

import me.abacate.animefoda.models.Comment
import me.abacate.animefoda.repositories.CommentRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/g/comments")
class CommentGetController(
    private val commentRepository: CommentRepository,
) {
    @GetMapping("/page/{pageId}")
    fun getCommentsByPage(@PathVariable("pageId") pageId: UUID): ApiResponse<List<Comment>> {
        return ApiResponse(data = commentRepository.getByPageId(pageId))
    }
}
