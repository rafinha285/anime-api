package me.abacate.animefoda.entities.comment

@Entity
@Table(name = "comments", schema = "users")
data class Comment(
    @Id
    @SequenceGenerator(name="comments_id_seq", sequenceName="comments_id_seq", allocationSize=1, schema = "users")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comments_id_seq")
    @Basic(optional = false)
    @Column(name = "id", updatable=false)
    val id:Int? = null,
    
//    @Column(name = "parent_id", nullable=true)
//    val parentId:Int? = null,
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    val parent: Comment? = null,
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonManagedReference
    val children: MutableList<Comment> = mutableListOf(),
    
    @Column(name = "page_id", nullable=false)
    val pageId:UUID? = null,
    
    @Column(name = "user_id", nullable=false)
    val userId:UUID? = null,
    
    @Column(name = "content", nullable=false)
    val content:String = "",
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Formula("(SELECT COUNT(*) FROM users.comments_likes cl WHERE cl.comment_id = id)")
    val totalLikes: Int = 0,
) {
    @get:JsonProperty("parentId")
    val parentId: Int?
        get() = parent?.id
    
    fun toResponse(): CommentResponse = CommentResponse(
        id = id!!,
        parentId = parentId!!,
        pageId = pageId!!,
        userId = userId!!,
        content = content,
        createdAt = createdAt,
        children = children.map { it.toResponse() },
        likes = totalLikes,
    )
    fun buildResponseHierarchy(commentMap: Map<Int, Comment>): CommentResponse {
        return CommentResponse(
            id = id!!,
            parentId = parent?.id,
            pageId = pageId!!,
            userId = userId!!,
            content = content,
            createdAt = createdAt,
            children = children
                .sortedByDescending { it.createdAt } // Ordenar por data
                .map { commentMap[it.id]!!.buildResponseHierarchy(commentMap) },
            likes = totalLikes,
        )
    }
}