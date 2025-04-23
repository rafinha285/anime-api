package me.abacate.animefoda.request

import java.util.*

data class NewSeasonRequest(
    val name: String,
    val anime_id: UUID,
    val index: Int,
)