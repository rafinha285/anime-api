package me.abacate.animefoda.entities.language

import com.neovisionaries.i18n.CountryCode
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Locale

interface LanguageRepository: JpaRepository<Language, Long> {
    fun findByName(name: String): Language?
    fun findByIsoCode(isoCode: String): Language?
    fun existsByIsoCode(isoCode: String): Boolean
}