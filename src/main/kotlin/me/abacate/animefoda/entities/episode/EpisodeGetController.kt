package me.abacate.animefoda.entities.episode

import me.abacate.animefoda.anime.Anime
import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.entities.user.UserService
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
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
    ): ApiResponse<List<Episode>> {
        var episodes: List<Episode>;
        if(jwt != null) {
            episodes = episodeService.getAllEpisodes()
        }else{
            episodes = episodeService.getAllEpisodesVisible()
        }
        return ApiResponse(data = episodes, message = if (jwt != null ) "Admin Action" else "" )
    }
    
    
}