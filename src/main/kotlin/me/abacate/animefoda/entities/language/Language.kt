package me.abacate.animefoda.entities.language

import com.neovisionaries.i18n.CountryCode
import jakarta.persistence.*
import java.util.Locale

@Entity
@Table(name = "language", schema = "anime")
open class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Short? = null,
    
    @Column(name = "name", nullable = false, unique = true)
    val name: String? = null,
    
    @Column(name = "localname", nullable = false, unique = true)
    val localName: String? = null,
    
//    @Enumerated(EnumType.STRING)
    @Column(name = "iso_code", nullable = false, unique = true)
    val isoCode: String? = null,
){
    fun toDTO(): LanguageDTO {
        return LanguageDTO(
            id = id!!,
            name = name!!,
            localName = localName!!,
            isoCode = isoCode!!
        )
    }
}
