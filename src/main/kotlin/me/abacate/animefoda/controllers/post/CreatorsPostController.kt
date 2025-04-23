package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.request.AddProducersRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.AnimeService
import me.abacate.animefoda.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
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
    ):ApiResponse<Creator> {
        if(!userService.isAdminAndSuperUser(UUID.fromString(jwt.subject))) {
            throw UnauthorizedResponse()
        }
        val anime = animeService.addCreatorToAnime(id, prod);
        val producer = anime.creators.find { producer: Creator -> producer.name == prod.name };
        return ApiResponse(data = producer,message = "Producer ${producer?.id} Added to ${anime.id}")
    }
}