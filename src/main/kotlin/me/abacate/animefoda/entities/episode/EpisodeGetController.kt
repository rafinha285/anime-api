package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.entities.user.UserService
import me.abacate.animefoda.errors.NotFoundResponse
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/g/episode")
class EpisodeGetController(
    val episodeService: EpisodeService,
    private val userService: UserService
) {
    @GetMapping("/all")
    fun getAll(
        @AuthenticationPrincipal jwt: Jwt? = null,
        @RequestParam count: Int? = null,
    ): ApiResponse<List<EpisodeDTO>> {
        return if(jwt != null && userService.containsRole(UUID.fromString(jwt.subject), RoleName.ROLE_ADMIN)) {
            ApiResponse(data = episodeService.getAllEpisodes(count).map { it.toDTO() }, message = "Admin Action")
        }else{
            ApiResponse(data = episodeService.getAllEpisodesVisible(count).map { it.toDTO() })
        }
    }
    
    
    @GetMapping("/{animeId}/{seasonId}/{id}")
    fun getEpisode(
        @PathVariable("id") episodeId: UUID,
        @AuthenticationPrincipal jwt: Jwt? = null,
    ): ApiResponse<Episode> {
        var episode: Episode?
        episode = if(jwt != null && userService.containsRole(UUID.fromString(jwt.subject), RoleName.ROLE_ADMIN)) {
            episodeService.getEpisode(episodeId)
        }else{
            episodeService.getEpisodeVisible(episodeId)
        }
        if(episode == null){
            throw NotFoundResponse(episodeId)
        }
        return ApiResponse(data = episode, message = if (jwt != null ) "Admin Action" else "" )
    }
    
}