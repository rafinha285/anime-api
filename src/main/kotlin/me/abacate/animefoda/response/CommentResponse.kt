package me.abacate.animefoda.response

import java.time.LocalDateTime
import java.util.*

data class CommentResponse(
    val id: Int,
    val parentId: Int?,
    val pageId: UUID,
    val userId: UUID,
    val content: String,
    val createdAt: LocalDateTime,
    val children: List<CommentResponse> = emptyList(),
    val likes: Int,
)