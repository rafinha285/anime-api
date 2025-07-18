package me.abacate.animefoda.loader

import me.abacate.animefoda.entities.language.Language
import me.abacate.animefoda.entities.language.LanguageRepository
import org.springframework.stereotype.Component
import com.neovisionaries.i18n.CountryCode
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import java.util.Locale

@Component
class LanguageLoader(
    private val languageRepository: LanguageRepository,
) {
    @Bean
    fun languageLoadData() = ApplicationRunner{
        val ptBR = Locale.of("pt", "BR")
        
        val localesWithCountry = Locale.getAvailableLocales()
            .filter { it.language.isNotBlank() && it.country.isNotBlank() }
            .distinctBy { "${it.language}_${it.country}" }
//        println(localesWithCountry.sortedBy{it.toLanguageTag()}.map{ "${ it.toLanguageTag() }, \n"})
        localesWithCountry.forEach { locale ->
             // ex: pt_BR
            val isoCode = locale.toLanguageTag()
            if (!languageRepository.existsByIsoCode(isoCode)) {
                
                val namePt = locale.getDisplayLanguage(ptBR)  // Nome em português (ex: "Inglês")
                val localName = locale.getDisplayLanguage(locale) // Nome local (ex: "English")
                
                println("Salvando idioma: $isoCode - $namePt / $localName")
                
                languageRepository.save(
                    Language(
                        name = namePt,
                        localName = localName,
                        isoCode = isoCode
                    )
                )
            }
        }
    }
}