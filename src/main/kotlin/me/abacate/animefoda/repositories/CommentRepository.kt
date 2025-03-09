package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.CommentModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository: JpaRepository<CommentModel, Int>{
    fun getByPageId(pageId: UUID): MutableList<CommentModel>
    
}
