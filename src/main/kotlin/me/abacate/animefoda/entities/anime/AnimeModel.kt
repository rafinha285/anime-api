package me.abacate.animefoda.anime

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import me.abacate.animefoda.character.Character
import me.abacate.animefoda.entities.creator.Creator
import me.abacate.animefoda.entities.producer.Producer
import me.abacate.animefoda.entities.season.Season
import me.abacate.animefoda.entities.state.State
import me.abacate.animefoda.entities.studio.Studio
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "anime", schema = "anime")
data class AnimeModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ou GenerationType.AUTO, conforme sua configuração
    @Column(name = "id", columnDefinition = "uuid")
    var id: UUID? = null,
    
    @Column(name = "averageeptime", nullable = false)
    var averageEpTime: Double = 0.0,
    
    @Column(name = "date_added", nullable = false)
    var dateAdded: OffsetDateTime = OffsetDateTime.now(),
    
    @Column(name = "description", nullable = false, columnDefinition = "text")
    var description: String = "",
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "genre", nullable = false, columnDefinition = "text[]")
    var genre: List<String> = emptyList(),
    
//    @Enumerated(EnumType.STRING)
    @Column(name = "language",  nullable = false)
    var language: String? = null, // enum que você deve definir
    
    @Column(name = "name", nullable = false, length = 255)
    var name: String = "",
    
    @Column(name = "name2", nullable = false, length = 255)
    var name2: String? = "",
    
//    @Enumerated(EnumType.STRING)
    @Column(name = "quality", nullable = false)
    var quality: String? = null, // enum que você deve definir
    
    @Column(name = "rating")
    var rating: Double? = null,
    
    @Column(name = "visible", nullable = false)
    var visible: Boolean = false,
    
//    @Enumerated(EnumType.STRING)
    @Column(name = "weekday")
    var weekday: String? = null, // enum que você deve definir
    
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "producers", columnDefinition = "uuid[]")
//    var producers: List<UUID> = emptyList(),
//
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "creators", columnDefinition = "uuid[]")
//    var creators: List<UUID> = emptyList(),
//
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "studios", columnDefinition = "uuid[]")
//    var studios: List<UUID> = emptyList(),
    
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_producers",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "producer_id")]
    )
    var producers: MutableSet<Producer> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_creators",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "creator_id")]
    )
    var creators: MutableSet<Creator> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_studios",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "studio_id")]
    )
    var studios: MutableSet<Studio> = mutableSetOf(),
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_characters",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")]
    )
    var characters: MutableSet<Character> = mutableSetOf(),
    
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "anime_state",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
//        inverseJoinColumns = [JoinColumn(name = "state_id")]
    )
    var state: State? = null,
//    @Enumerated(EnumType.STRING)
//    @Column(name = "state")
//    var state: State? = null, // enum que você deve definir
    
    @Column(name = "releasedate")
    var releaseDate: LocalDate? = null,
    
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "seasons",
        schema = "anime",
        joinColumns = [JoinColumn(name = "anime_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    var seasons: MutableSet<Season> = mutableSetOf(),
)