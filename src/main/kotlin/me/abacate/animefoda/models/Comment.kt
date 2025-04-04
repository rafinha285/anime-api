package me.abacate.animefoda.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "comments", schema = "users")
data class Comment(
    @Id
    @SequenceGenerator(name="comments_id_seq", sequenceName="comments_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comments_id_seq")
    @Basic(optional = false)
    @Column(name = "id", updatable=false)
    val id:Int,
    
    @Column(name = "parent_id")
    val parentId:Int,
    
    @Column(name = "page_id")
    val pageId:UUID,
    
    @Column(name = "user_id")
    val userId:UUID,
    
    @Column(name = "content")
    val content:String,
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val createdAt: LocalDateTime,
)
