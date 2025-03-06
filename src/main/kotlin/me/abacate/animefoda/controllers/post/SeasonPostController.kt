package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.errors.InternalServerErrorResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.models.SeasonModel
import me.abacate.animefoda.repositories.SeasonRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/p/seasons")
class SeasonPostController (
    private val seasonRepository: SeasonRepository,
    private val jwtUtil: JWTUtil
) {
    @PostMapping("/new")
    fun newSeason(
        @RequestBody season: SeasonModel,
        @RequestHeader(name = "User-Agent") userAgent: String,
        @RequestHeader(name = "timeZone") timeZone: String,
        @RequestHeader(name = "webGlRenderer") webGlRenderer: String,
        @RequestHeader(name = "webGlVendor") webGlVendor: String,
        @CookieValue(name = "token") token: String
    ):ApiResponse<String?> {
        try {
            if(jwtUtil.checkToken(token, userAgent, timeZone, webGlVendor, webGlRenderer)){
                try{
                    seasonRepository.save(season)
                    
                }catch (e:Exception){
                    throw InternalServerErrorResponse(e.localizedMessage)
                }
                return ApiResponse(success = true)
            }else{
                throw UnauthorizedResponse()
            }
        }catch (e: Exception){
            return ApiResponse(success = false, message = e.message)
        }
    }
}
