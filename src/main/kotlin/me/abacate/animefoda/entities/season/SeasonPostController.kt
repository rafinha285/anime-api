package me.abacate.animefoda.entities.season

import me.abacate.animefoda.annotation.AdminAction
import me.abacate.animefoda.entities.role.RoleName
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.request.NewSeasonRequest
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.services.JWTService
import me.abacate.animefoda.entities.user.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/p/season")
class SeasonPostController(
    private val seasonRepository: SeasonRepository,
    private val jwtService: JWTService,
    private val userService: UserService
) {
    @PostMapping("/new")
    @AdminAction("CREATE SEASON animeId={body.anime_id}")
    fun newSeason(
        @RequestBody body: NewSeasonRequest,
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<Season> {
        val userUUID = UUID.fromString(jwt.subject)
        if(!userService.containsRole(userUUID, RoleName.ROLE_ADMIN)){
            throw UnauthorizedResponse()
        }
        
        val season = Season(
            name = body.name,
            index = body.index,
            animeId = body.anime_id,
        )
        seasonRepository.save(season)
        return ApiResponse(data = season, message = "Season ${season.id} created")
    }
}