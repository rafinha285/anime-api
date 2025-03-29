package me.abacate.animefoda.repositories

import me.abacate.animefoda.enums.RoleEnum
import me.abacate.animefoda.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository:JpaRepository<UserModel, UUID> {
    @Query(
        value = """
            WITH hashed_password AS (
                SELECT users.crypt(:password, salt) AS hash
                FROM users.users
                WHERE email = :email
            )
            SELECT *
            FROM users.users
            WHERE email = :email AND password = (SELECT hash FROM hashed_password)
        """,
        nativeQuery = true
    )
    fun findByEmailAndPassword(
        @Param("email") email: String,
        @Param("password")password: String
    ): UserModel?
    
    fun findByEmail(email: String): UserModel?
    
//    @Query(
//        value = """
//            SELECT  FROM users.users WHERE _id = :id AND ARRAY_CONTAINS()
//        """,
//        nativeQuery = true
//    )
//    fun isInRole(
//        @Param("id") id: UUID,
//        roleEnum: RoleEnum
//    ): Boolean
//    fun findByEmailAndPassword(email: String, password: String): UserModel? {
//        // A query utiliza parâmetros posicionais: ? serão substituídos na ordem
//        val sql = """
//            WITH hashed_password AS (
//                SELECT users.crypt(password, salt) AS hash
//                FROM users.users
//                WHERE email = :email
//            )
//            SELECT id, email, password, salt
//            FROM users.users
//            WHERE email = :email AND password = (SELECT hash FROM hashed_password)
//        """.trimIndent()
//
//        return try {
//
//            val namedParameters: SqlParameterSource = MapSqlParameterSource()
//                .addValue("email", email)
//                .addValue("password", password)
//
//            jdbcTemplate.queryForObject(sql,namedParameters,UserModel::class.java) {rs ->}
////            jdbcTemplate.queryForObject(sql, arrayOf(password, email, email)) { rs, _ ->
////                UserModel(
////                    id = UUID.fromString(rs.getString("id")),
////                    email = rs.getString("email"),
////                    password = rs.getString("password"),
////                    salt = rs.getString("salt")
////                    // preencha os demais campos se necessário
////                )
////            }
//        } catch (e: EmptyResultDataAccessException) {
//            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
//            null
//        }
//    }

}
