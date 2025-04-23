package me.abacate.animefoda.exceptionhandler

import me.abacate.animefoda.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class GlobalExceptionHandler {
    
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<ApiResponse<String>> {
        val apiResponse = ApiResponse<String>(
            success = false,
            message = ex.reason ?: "Erro desconhecido"
        )
        return ResponseEntity(apiResponse, ex.statusCode)
    }
    
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<String>> {
        val apiResponse = ApiResponse<String>(
            success = false,
            message = ex.message ?: "Erro interno do servidor"
        )
        println(ex.message)
        return ResponseEntity(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
