package me.abacate.animefoda.serialized

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class UserAnimeListId(
    val userId: UUID = UUID.randomUUID(),
    val animeId: UUID = UUID.randomUUID()
) : Serializable
