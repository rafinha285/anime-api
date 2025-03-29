package me.abacate.animefoda.response

import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.models.Studio

data class AnimeDetailsResponse(
    val producers: List<Producer>,
    val creators: List<Creator>,
    val studios: List<Studio>
)
