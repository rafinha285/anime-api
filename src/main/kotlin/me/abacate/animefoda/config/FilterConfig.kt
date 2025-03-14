package me.abacate.animefoda.config

import me.abacate.animefoda.filters.JwtSessionValidationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig(private val jwtSessionValidationFilter: JwtSessionValidationFilter) {
    
    @Bean
    fun jwtFilterRegistration(): FilterRegistrationBean<JwtSessionValidationFilter> {
        val registrationBean = FilterRegistrationBean(jwtSessionValidationFilter)
        // Defina os padrões de URL nos quais o filtro será aplicado:
        registrationBean.addUrlPatterns("/g/user/**")
        return registrationBean
    }
}
