package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CommentRepository: JpaRepository<Comment, Int>{
    @Query("""
        SELECT c FROM Comment c
        LEFT JOIN FETCH c.children
        WHERE (:pageId IS NULL OR c.pageId = :pageId)
        AND (:userId IS NULL OR c.userId = :userId)
    """)
    fun findCommentsWithFilters(
        @Param("pageId") pageId: UUID?,
        @Param("userId") userId: UUID?,
    ): List<Comment>
    fun getByPageId(pageId: UUID): MutableList<Comment>
    fun getByUserId(userId: UUID): MutableList<Comment>
    fun getCommentById(id: Int): MutableList<Comment>
}
