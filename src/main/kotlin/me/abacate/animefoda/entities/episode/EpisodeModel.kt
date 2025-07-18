package me.abacate.animefoda.entities.episode

import jakarta.persistence.*
import me.abacate.animefoda.anime.AnimeModel
import me.abacate.animefoda.anime.AnimeRepository
import me.abacate.animefoda.anime.AnimeService
import me.abacate.animefoda.entities.language.Language
import me.abacate.animefoda.entities.season.Season
import java.time.OffsetDateTime
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "episodes", schema = "anime")
open class EpisodeModel (
    
    @Id
    @Column(name = "id")
    val id: UUID? = null,
    
//    @Column(name = "anime_id", nullable = false)
//    val animeId: UUID? = null,
//
//    @Column(name = "season_id", nullable = false)
//    val seasonId: UUID? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", insertable = false, updatable = false)
    val anime: AnimeModel? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", insertable = false, updatable = false)
    val season: Season? = null,
    
    @Column(name = "date_added", nullable = false)
    val dateAdded: OffsetDateTime? = OffsetDateTime.now(),
    
    @Column(name = "duration", nullable = false)
    val duration: Double? = null,
    
    @Column(name = "ending")
    val ending: Int? = null,
    
    @Column(name = "epindex", nullable = false)
    val epIndex: Int? = null,
    
    @Column(name = "name", nullable = false)
    val name: String? = null,
    
    @Column(name = "opening_end")
    val openingEnd: Int? = null,
    
    @Column(name = "opening_start")
    val openingStart: Int? = null,
    
    @Column(name = "release_date", nullable = false)
    val releaseDate: Date? = null,
    
    @Column(name = "visible", nullable = false)
    val visible: Boolean = false,
    
    @Column(name = "resolution", nullable = false)
    val resolution: String? = null,
    
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "episode_audio_tracks",
        schema = "anime",
        joinColumns = [JoinColumn(name = "episode_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    val audioTracks: List<Language> = listOf(),
    
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "episode_subtitle_tracks",
        schema = "anime",
        joinColumns = [JoinColumn(name = "episode_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    val subtitleTracks: List<Language> = listOf()
) {
    fun toDTO():EpisodeDTO {
        return EpisodeDTO(
            id = id!!,
            animeId = anime?.id!!,
            seasonId = season?.id!!,
            dateAdded = dateAdded!!,
            duration = duration!!,
            ending = ending!!,
            index = epIndex!!,
            name = name!!,
            openingEnd = openingEnd!!,
            openingStart = openingStart!!,
            releaseDate = releaseDate!!,
            visible = visible,
            audioTracks = audioTracks.map { it.toDTO() },
            subtitleTracks = subtitleTracks.map { it.toDTO() },
            animeTitle = anime?.name!!,
            seasonTitle = season?.name!!,
            resolution = resolution!!
        )
    }
}