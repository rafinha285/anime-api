package me.abacate.animefoda.exceptionHandler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.abacate.animefoda.response.ApiResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.setHeader("WWW-Authenticate", "")
        val errorResponse = ApiResponse<String>(
            success = false,
            message = "Unauthorized: ${authException?.message}",
        )
        response.writer.write(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}
