package me.abacate.animefoda.entities.creator

import me.abacate.animefoda.anime.AnimeService
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.request.AddProducersRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/p/creators")
class CreatorsPostController(
    private val userService: UserService,
    private val animeService: AnimeService
) {
    @PostMapping("/anime/add/{id}")
    fun addProducerToAnime(
        @PathVariable id: UUID,
        @RequestBody prod: AddProducersRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ApiResponse<Creator> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) {
            throw UnauthorizedResponse()
        }
        val anime = animeService.addCreatorToAnime(id, prod);
        val producer = anime.creators.find { producer: Creator -> producer.name == prod.name };
        return ApiResponse(data = producer, message = "Producer ${producer?.id} Added to ${anime.id}")
    }
}