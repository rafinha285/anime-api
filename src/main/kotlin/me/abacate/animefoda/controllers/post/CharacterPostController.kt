package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.models.Character
import me.abacate.animefoda.services.CharacterService
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/p/character")
class CharacterPostController(
    private val userService: UserService,
    private val characterService: CharacterService
) {
    @PostMapping("/anime/new/{id}")
    fun newCharacter(
        @RequestBody newCharacter: Character,
        @PathVariable id: UUID,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Character> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) {
            throw UnauthorizedResponse()
        }
        return ApiResponse(data = characterService.addToAnime(id,newCharacter), message = "Character created and added to anime: $id")
    }
}