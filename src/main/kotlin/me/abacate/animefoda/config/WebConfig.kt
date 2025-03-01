package me.abacate.animefoda.config

import me.abacate.animefoda.RequestInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val requestInterceptor: RequestInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestInterceptor)
            .addPathPatterns("/g/user/**", "/p/user/**") // Defina os padrões de URL que o interceptor deve interceptar
    }
}
