package me.abacate.animefoda.request

import java.util.*

data class NewCommentRequest (
    val parentId: Long?,
    val pageId: UUID,
    val content: String,
)