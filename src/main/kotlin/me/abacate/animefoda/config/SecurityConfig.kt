package me.abacate.animefoda.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtTimestampValidator
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfigurationSource
import javax.crypto.spec.SecretKeySpec
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

@Configuration
class SecurityConfig {
    
    @Value("\${spring.security.oauth2.resourceserver.jwt.secret}")
    private lateinit var secret: String
    
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/g/user/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { resourceServer -> resourceServer.jwt(Customizer.withDefaults())}
            .sessionManagement {session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        
        return http.build()
    }
        
        @Bean
    fun jwtDecoder(): JwtDecoder {
        // Cria a chave secreta para o algoritmo HS256
        val secretKey = SecretKeySpec(secret.toByteArray(), "HMACSHA256")
        val decoder = NimbusJwtDecoder.withSecretKey(secretKey).build()
        // Configura um clock skew se necessário (por exemplo, 300 segundos = 5 minutos)
        val clockSkew = 300.seconds.toJavaDuration()
        val timestampValidator = JwtTimestampValidator(clockSkew)
        val validators: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(timestampValidator)
        decoder.setJwtValidator(validators)
        return decoder
    }
}
