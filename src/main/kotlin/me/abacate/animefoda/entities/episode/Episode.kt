package me.abacate.animefoda.entities.episode

import jakarta.persistence.*
import me.abacate.animefoda.anime.Anime
import me.abacate.animefoda.entities.language.Language
import me.abacate.animefoda.entities.season.Season
import me.abacate.animefoda.enums.QualityNumbers
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "episodes", schema = "anime")
open class Episode(
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
    val dateAdded: OffsetDateTime = OffsetDateTime.now(),
    
    @Column(nullable = false)
    val duration: Double = 0.0,
    
    @Column
    val ending: Int? = null,
    
    @Column(name = "epindex", nullable = false)
    val epIndex: Int = 1,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(name = "openingend")
    val openingEnd: Int? = null,
    
    @Column(name = "openingstart")
    val openingStart: Int? = null,
    
    @Column(name = "releasedate")
    val releaseDate: Date = Date(),
    
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "subtitletracks")
//    val subtitlesTracks: List<String> = listOf(),
//
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(name = "audiotracks")
//    val audioTracks: List<String> = listOf(),
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "episode_audio_tracks",
        schema = "anime",
        joinColumns = [JoinColumn(name = "episode_id")],
        inverseJoinColumns = [JoinColumn(name = "language_id")]
    )
    val audioTracks: List<Language> = listOf(),
    
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "episode_subtitle_tracks",
        schema = "anime",
        joinColumns = [JoinColumn(name = "episode_id")],
        inverseJoinColumns = [JoinColumn(name = "language_id")]
    )
    val subtitleTracks: List<Language> = listOf(),
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(nullable = false)
    val resolution: List<String> = listOf(QualityNumbers.Q1920X1080.label),
    
    @Column(nullable = false)
    val visible: Boolean = false,
) {
    fun toDTO(): EpisodeDTO {
        return EpisodeDTO(
            id = this.id!!,
            animeId = this.anime?.id!!,
            animeTitle = this.anime.name,
            seasonId = this.season?.id!!,
            seasonTitle = this.season.name!!,
            dateAdded = this.dateAdded,
            duration = this.duration,
            ending = this.ending,
            epIndex = this.epIndex,
            name = this.name,
            openingStart = this.openingStart,
            openingEnd = this.openingEnd,
            releaseDate = this.releaseDate,
            subtitleTracks = this.subtitleTracks.map { it.toDTO() },
            audioTracks = this.audioTracks.map { it.toDTO() },
            resolution = this.resolution,
            visible = this.visible
        )
    }
}
