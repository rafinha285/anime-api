package me.abacate.animefoda.entities.episode

import jakarta.persistence.*
import me.abacate.animefoda.anime.Anime
import me.abacate.animefoda.entities.season.Season
import me.abacate.animefoda.enums.QualityNumbers
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "episodes", schema = "anime")
data class Episode(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    val id: UUID? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", insertable = false, updatable = false)
    val anime: Anime? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", insertable = false, updatable = false)
    val season: Season? = null,
    
    @Column(name = "date_added", nullable = false)
    val dateAdded: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val duration: Double = 0.0,
    
    @Column
    val ending: Int? = null,
    
    @Column(name = "epindex", nullable = false)
    val epIndex: Int = 0,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(name = "openingend")
    val openingEnd: Int? = null,
    
    @Column(name = "openingstart")
    val openingStart: Int? = null,
    
    @Column(name = "releasedate")
    val releaseDate: Date = Date(),
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "subtitletracks")
    val subtitlesTracks: List<String> = listOf(),
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "audiotracks")
    val audioTracks: List<String> = listOf(),

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    val resolution: List<QualityNumbers> = listOf(QualityNumbers._1920X1080),
    
    @Column(nullable = false)
    val visible: Boolean = false,
)
