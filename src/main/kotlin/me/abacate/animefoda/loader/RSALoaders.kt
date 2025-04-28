package me.abacate.animefoda.loader

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

@Component
class RSALoaders(){
    fun loadRSAPrivateKey(path: String): RSAPrivateKey {
        // Carrega o arquivo da chave privada da classpath
        val resource = ClassPathResource(path.removePrefix("classpath:"))
        val keyBytes = resource.inputStream.use { it.readBytes() }
        // Converte o conteúdo do PEM para String
        val keyString = String(keyBytes)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyString)
        val keySpec = PKCS8EncodedKeySpec(decoded)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(keySpec) as RSAPrivateKey
    }
    
    fun loadRSAPublicKey(path: String): RSAPublicKey {
        // Carrega o arquivo da chave pública da classpath
        val resource = ClassPathResource(path.removePrefix("classpath:"))
        val keyBytes = resource.inputStream.use { it.readBytes() }
        // Converte o conteúdo do PEM para String
        val keyString = String(keyBytes)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyString)
        val keySpec = X509EncodedKeySpec(decoded)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(keySpec) as RSAPublicKey
    }
}