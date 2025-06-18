package me.abacate.animefoda.response

import me.abacate.animefoda.entities.creator.Creator
import me.abacate.animefoda.entities.producer.Producer
import me.abacate.animefoda.entities.studio.Studio

data class AnimeDetailsResponse(
    val producers: List<Producer>,
    val creators: List<Creator>,
    val studios: List<Studio>
)
