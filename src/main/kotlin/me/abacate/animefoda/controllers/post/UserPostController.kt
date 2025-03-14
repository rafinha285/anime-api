package me.abacate.animefoda.controllers.post

import me.abacate.animefoda.jwt.JWTUtil
import me.abacate.animefoda.repositories.UserRepository
import me.abacate.animefoda.response.ApiResponse
import me.abacate.animefoda.response.AuthResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.web.bind.annotation.*
import java.time.Instant

data class LoginRequestEntity(
    val email: String,
    val password: String,
    val timeZone: String,
    val WebGLVendor: String,
    val WebGLRenderer: String,
)

@RestController
@RequestMapping("/p/user")
class UserPostController(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil,
) {
    //    @PostMapping("/login")
//    fun login(
//        @RequestBody userSession: LoginRequestEntity,
//        @RequestHeader(name = "User-Agent") userAgent: String,
//        response: HttpServletResponse
//    ): ApiResponse<AuthResponse> {
//        val find = userRepository.findByEmailAndPassword(email = userSession.email, password = userSession.password)
//        if(find == null){
//            throw UnauthorizedResponse()
//        }
//        println(find.id)
//        if(userAgent == ""){
//            throw BadRequestResponse()
//        }
//        val jwtResponse = jwtUtil.generateToken(userSession,userAgent)
//        val cookie = Cookie("token", jwtResponse.token)
//        cookie.path = "/"
//        response.addCookie(cookie)
//        return ApiResponse(data = jwtResponse)
//    }
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequestEntity: LoginRequestEntity,
        @RequestHeader("User-Agent", required = true) userAgent: String,
    ):ApiResponse<AuthResponse>{
        
        val jwtValue = jwtUtil.generateToken(loginRequestEntity, userAgent)
    
        return ApiResponse(
            data = AuthResponse(
                accessToken = jwtValue.jwt.tokenValue,
                refreshToken = jwtValue.refreshToken,
                expiresIn = jwtValue.expiresIn
            )
        )
    }
}
