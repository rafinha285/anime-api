package me.abacate.animefoda.response

import me.abacate.animefoda.models.CreatorsModel
import me.abacate.animefoda.models.ProducersModel
import me.abacate.animefoda.models.StudiosModel

data class AnimeDetailsResponse(
    val producers: List<ProducersModel>,
    val creators: List<CreatorsModel>,
    val studios: List<StudiosModel>
)
