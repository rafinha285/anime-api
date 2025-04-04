package me.abacate.animefoda.controllers.post

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.services.JWTService
import me.abacate.animefoda.models.Season
import me.abacate.animefoda.repositories.SeasonRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/p/seasons")
class SeasonPostController (
    private val seasonRepository: SeasonRepository,
    private val jwtService: JWTService
) {
    @PostMapping("/new")
    fun newSeason(
        @RequestBody season: Season,
        request: HttpServletRequest,
        response: HttpServletResponse,
        @CookieValue(name = "token") token: String
    ):ApiResponse<String?> {
        try {
//            jwtUtil.checkToken(token, request, response)
            seasonRepository.save(season)
            return ApiResponse()
        }catch (e: Exception){
            return ApiResponse(success = false, message = e.message)
        }
    }
}
