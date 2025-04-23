package me.abacate.animefoda.controllers

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping("/")
    fun home(
        httpServletResponse: HttpServletResponse,
    ) {
        return httpServletResponse.sendRedirect("https://animefoda.top")
    }
}
