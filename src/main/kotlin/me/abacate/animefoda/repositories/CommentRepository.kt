package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository: JpaRepository<Comment, Int>{
    fun getByPageId(pageId: UUID): MutableList<Comment>
    
}
