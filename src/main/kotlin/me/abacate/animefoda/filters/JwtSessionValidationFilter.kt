package me.abacate.animefoda.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.errors.UnauthorizedResponse
import me.abacate.animefoda.repositories.UserSessionRepository
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.util.*

@Component
class JwtSessionValidationFilter(
    private val jwtDecoder: JwtDecoder,
    private val userSessionRepository: UserSessionRepository
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ){
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.removePrefix("Bearer ").trim()
            try{
                val jwt = jwtDecoder.decode(token)
                
                val sessionId = UUID.fromString(jwt.getClaim<String>("session_id").toString())
                
                
                val session = userSessionRepository.findBySessionIdAndEnabled(sessionId,UUID.fromString(jwt.subject.toString()))
                if(session == null){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Session")
                    return
                }
                println(session.sessionId)
                
                if(session.expiresAt.isBefore(LocalDateTime.now())){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired Session")
                    return
                }
                
                val headerTimeZone = request.getHeader("timeZone") ?: ""
                val headerWebGlVendor = request.getHeader("WebGLVendor") ?: ""
                val headerWebGlRenderer = request.getHeader("WebGLRenderer") ?: ""
                val headerUserAgent = request.getHeader("User-Agent") ?: ""
                
                if (session.timeZone != headerTimeZone ||
                    session.webGlVendor != headerWebGlVendor ||
                    session.webGlRenderer != headerWebGlRenderer ||
                    session.userAgent != headerUserAgent
                ) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Parâmetros de sessão não correspondem")
                    return
                }
                
            }catch(ex: JwtException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido: ${ex.message}")
                return
            }catch(ex: Exception){
                UnauthorizedResponse()
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erro na validação da sessão: ${ex.message}")
                return
            }
        }
        
        filterChain.doFilter(request, response)
    }
}
