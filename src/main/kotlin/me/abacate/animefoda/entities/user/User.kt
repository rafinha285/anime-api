package me.abacate.animefoda.entities.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import me.abacate.animefoda.anime.Anime
import me.abacate.animefoda.entities.role.Role
import me.abacate.animefoda.entities.user.animelist.UserAnimelist
import me.abacate.animefoda.request.LoginRequest
import me.abacate.animefoda.response.UserResponse
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users",schema = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="_id")
    val id: UUID? = null,
    
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
    
//    @JdbcTypeCode(SqlTypes.ARRAY)
//    @Column(
//        name = "role",
//        columnDefinition = "role_enum[]"
//    )
//
//    val roleEnums:List<RoleEnum> =emptyList(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        schema = "users",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<Role> = mutableSetOf(),
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_anime_list",
        schema = "users",
        joinColumns = [
            JoinColumn(name = "user_id", referencedColumnName = "_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "anime_id", referencedColumnName = "anime_id")
        ]
    )
    val animeList: MutableSet<UserAnimelist> = mutableSetOf()
){
    fun isLoginCorrect(loginRequest: LoginRequest, passwordEncoder: PasswordEncoder):Boolean{
        return passwordEncoder.matches(loginRequest.password,this.password)
    }
    
    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id,
            name = this.name,
            email = this.email,
            surname = this.surname,
            username = this.username,
            birthdate = this.birthdate,
            roles = this.roles,
            animelist = this.animeList
        )
    }
}