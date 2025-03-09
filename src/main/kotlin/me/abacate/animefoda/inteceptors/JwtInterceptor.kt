package me.abacate.animefoda.inteceptors

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.jwt.JWTUtil
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(
    private val jwtUtil: JWTUtil
):HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader("Authorization")
        println(request.getHeader("Authorization"))
        val userAgent = request.getHeader("User-Agent") ?: ""
        val timeZone = request.getHeader("timeZone") ?: ""
        val webGlRenderer = request.getHeader("webGlRenderer") ?: ""
        val webGlVendor = request.getHeader("webGlVendor") ?: ""
        
        if (token.isNullOrEmpty() || userAgent.isEmpty() || timeZone.isEmpty() || webGlRenderer.isEmpty() || webGlVendor.isEmpty()) {
            throw UnauthorizedResponse()
            return false
        }
        
        if (token == null || !jwtUtil.checkToken(token, userAgent, timeZone, webGlVendor, webGlRenderer)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized $token")
            return false
        }
        
        return true
    }
}
