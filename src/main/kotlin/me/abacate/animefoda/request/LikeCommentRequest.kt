package me.abacate.animefoda.request

import java.util.UUID

data class LikeCommentRequest(
    val commentId: Long,
    val pageId: UUID
)