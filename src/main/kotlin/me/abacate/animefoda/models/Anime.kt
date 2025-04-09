package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.enums.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "anime", schema = "anime")
data class Anime(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ou GenerationType.AUTO, conforme sua configuração
    @Column(name = "id", columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID(),
    
    @Column(name = "averageeptime", nullable = false)
    val averageEptime: Double = 0.0,
    
    @Column(name = "date_added", nullable = false)
    val dateAdded: OffsetDateTime = OffsetDateTime.now(),
    
    @Column(name = "description", nullable = false, columnDefinition = "text")
    val description: String = "",
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "genre", nullable = false, columnDefinition = "text[]")
    val genre: List<String> = emptyList(),
    
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    val language: Language = Language.Legendado, // enum que você deve definir
    
    @Column(name = "malid")
    val malid: Int? = null,
    
    @Column(name = "name", nullable = false, length = 255)
    val name: String = "",
    
    @Column(name = "name2", nullable = false, length = 255)
    val name2: String = "",
    
    @Enumerated(EnumType.STRING)
    @Column(name = "quality", nullable = false)
    val quality: Quality = Quality.`1080p`, // enum que você deve definir
    
    @Column(name = "rating")
    val rating: Double? = null,
    
    @Column(name = "visible", nullable = false)
    val visible: Boolean = false,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "weekday")
    val weekday: Weekday? = null, // enum que você deve definir
    
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "producers", columnDefinition = "uuid[]")
//    val producers: List<UUID> = emptyList(),
//
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "creators", columnDefinition = "uuid[]")
//    val creators: List<UUID> = emptyList(),
//
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "studios", columnDefinition = "uuid[]")
//    val studios: List<UUID> = emptyList(),
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_producers",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "producer_id")]
    )
    val producers: MutableSet<Producer> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_creators",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "creator_id")]
    )
    val creators: MutableSet<Creator> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_studios",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "studio_id")]
    )
    val studios: MutableSet<Studio> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_characters",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")]
    )
    val characters: MutableSet<Character> = mutableSetOf(),
    
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_state",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
//        inverseJoinColumns = [JoinColumn(name = "state_id")]
    )
    var state: State? = null,
//    @Enumerated(EnumType.STRING)
//    @Column(name = "state")
//    val state: State? = null, // enum que você deve definir
    
    @Column(name = "releasedate")
    val releaseDate: LocalDate? = null,
    
    @OneToMany(mappedBy = "anime_id", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val seasons: List<Season>? = emptyList()
)
