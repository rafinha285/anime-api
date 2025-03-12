package me.abacate.animefoda.config

import me.abacate.animefoda.RequestInterceptor
//import me.abacate.animefoda.inteceptors.JwtInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//@Configuration
//class WebConfig(@Autowired private val jwtInterceptor: JwtInterceptor) : WebMvcConfigurer {
//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(jwtInterceptor)
//            .addPathPatterns("/g/user/**", "/p/user/**","/p/seasons/new") // Defina os padrões de URL que o interceptor deve interceptar
//            .excludePathPatterns("/p/user/login")
//    }
//}
