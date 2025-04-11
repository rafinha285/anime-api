package me.abacate.animefoda.request

import me.abacate.animefoda.enums.Quality
import me.abacate.animefoda.enums.StateName
import java.time.LocalDate

data class NewAnimeRequest(
    val name: String,
    val name2: String?,
    val description: String,
    val producers: List<String>,
    val studios: List<String>,
    val creators: List<String>,
    val gens: List<String>,
    val releaseDate: LocalDate,
    val quality: String,
    val state: StateName,
    val language: String,
)
