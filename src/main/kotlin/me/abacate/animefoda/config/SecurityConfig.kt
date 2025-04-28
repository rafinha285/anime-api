package me.abacate.animefoda.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import me.abacate.animefoda.exceptionhandler.JwtAuthenticationEntryPoint
import me.abacate.animefoda.loader.RSALoaders
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.SecurityFilterChain
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class SecurityConfig(
    val rsaLoaders: RSALoaders
) {
    
    @Value("\${spring.security.oauth2.resourceserver.jwt.secret}")
    private lateinit var secret: String
    
    
    @Value("\${jwt.private.key}")
    private lateinit var privateKeyPath: String;
    
    @Value("\${jwt.public.key}")
    private lateinit var publicKeyPath: String;
    
    @Bean
    fun rsaPrivateKey(): RSAPrivateKey = rsaLoaders.loadRSAPrivateKey(privateKeyPath)
    
    @Bean
    fun rsaPublicKey(): RSAPublicKey = rsaLoaders.loadRSAPublicKey(publicKeyPath)
    
    //filtro de oauth2 para rotas especificas
    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint): SecurityFilterChain {
        
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/g/user/**").authenticated()
                    .requestMatchers("/p/animelist/**").authenticated()
                    .requestMatchers("/p/comment/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt(Customizer.withDefaults())
                resourceServer.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .sessionManagement {session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        
        return http.build()
    }
    
    //encoder de jwt
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk:JWK = RSAKey.Builder(this.rsaPublicKey())
            .privateKey(this.rsaPrivateKey())
            .build();
        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }
    
    //decoder do jwt
    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey()).build()
    }
    
    @Bean
    fun bCryptPasswordEncoder():BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }
}
