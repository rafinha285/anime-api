package me.abacate.animefoda.models

import jakarta.persistence.*
import me.abacate.animefoda.controllers.post.LoginRequestEntity
import me.abacate.animefoda.enums.Role
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users",schema = "users")
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="_id")
    val id: UUID = UUID.randomUUID(),
    
    @Column(name="name",length=255)
    val name: String = "",
    
    @Column(name="email", length=255)
    val email: String = "",
    
    @Column(name="surname", columnDefinition = "text")
    val surname: String = "",
    
    @Column(name="username", length = 100)
    val username: String = "",
    
    @Column(name = "birthdate")
    val birthdate: LocalDate = LocalDate.now(),

    @Column(name = "password", length = 255)
    val password: String = "",

    @Column(name="salt", length = 255)
    val salt: String = "",
    
    @Column(name="superuser", columnDefinition = "boolean")
    val superuser: Boolean = false,
    
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(
        name = "role",
        columnDefinition = "role_enum[]"
    )
    
    val roles:List<Role> =emptyList(),
){
    fun isLoginCorrect(loginRequest:LoginRequestEntity, passwordEncoder: PasswordEncoder):Boolean{
        return passwordEncoder.matches(loginRequest.password,this.password)
    }
}
