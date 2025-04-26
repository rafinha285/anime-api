package me.abacate.animefoda.request

import java.util.*

data class NewCommentRequest (
    val parentId: Int?,
    val pageId: UUID,
    val content: String,
)