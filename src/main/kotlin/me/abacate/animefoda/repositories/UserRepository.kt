package me.abacate.animefoda.repositories

import me.abacate.animefoda.models.UserModel
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Repository
class UserRepository(private val jdbcTemplate: NamedParameterJdbcTemplate){
    fun findByEmailAndPassword(email: String, password: String): UserModel? {
        // A query utiliza parâmetros posicionais: ? serão substituídos na ordem
        val sql = """
            WITH hashed_password AS (
                SELECT users.crypt(:password, salt) AS hash
                FROM users.users
                WHERE email = :email
            )
            SELECT *
            FROM users.users
            WHERE email = :email AND password = (SELECT hash FROM hashed_password)
        """.trimIndent()
        
        return try {
            
            val namedParameters = mutableMapOf(email to "email",password to "password")
            
            val dbRes = jdbcTemplate.queryForObject(sql,namedParameters,UserModel::class.java)
            dbRes
//            jdbcTemplate.queryForObject(sql, arrayOf(password, email, email)) { rs, _ ->
//                UserModel(
//                    id = UUID.fromString(rs.getString("id")),
//                    email = rs.getString("email"),
//                    password = rs.getString("password"),
//                    salt = rs.getString("salt")
//                    // preencha os demais campos se necessário
//                )
//            }
        } catch (e: EmptyResultDataAccessException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            null
        }
    }
}
