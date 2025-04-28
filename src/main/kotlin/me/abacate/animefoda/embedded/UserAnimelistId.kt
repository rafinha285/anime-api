package me.abacate.animefoda.embedded

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class UserAnimelistId(
    val userId: UUID,
    val animeId: UUID
) : Serializable{
    protected constructor() : this(
        UUID.fromString("00000000-0000-0000-0000-000000000000"),
        UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
}
