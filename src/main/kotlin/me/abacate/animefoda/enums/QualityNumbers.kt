package me.abacate.animefoda.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class QualityNumbers(val label: String) {
    Q3840X2160("3840x2160"),
    Q1920X1080("1920x1080"),
    Q1280X720("1280x720"),
    Q854X480("854x480");
    
    @JsonValue
    override fun toString() = label
}
