package me.abacate.animefoda.request

import me.abacate.animefoda.enums.Language
import me.abacate.animefoda.enums.Quality
import me.abacate.animefoda.enums.State
import java.time.LocalDate

data class NewAnimeRequest(
    val name: String,
    val name2: String,
    val description: String,
    val producers: List<String>,
    val creators: List<String>,
    val studios: List<String>,
    val gens: List<String>,
    val releasedate: LocalDate,
    val quality: Quality,
    val state: State,
    val language: Language,
)
