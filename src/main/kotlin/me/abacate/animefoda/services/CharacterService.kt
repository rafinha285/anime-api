package me.abacate.animefoda.services

import me.abacate.animefoda.models.Character
import me.abacate.animefoda.repositories.AnimeRepository
import me.abacate.animefoda.repositories.CharacterRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CharacterService(
    private val animeRepository: AnimeRepository,
    private val characterRepository: CharacterRepository,
) {
    fun addToAnime(id:UUID, characterParam: Character): Character {
        val anime = animeRepository.findById(id)
        .orElseThrow { RuntimeException("Anime not found!") }
        val character = characterRepository.save(characterParam)
        anime.characters.add(character)
        return character
    }
}