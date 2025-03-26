package me.abacate.animefoda

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class AnimefodaApplication
fun main(args: Array<String>) {
    runApplication<AnimefodaApplication>(*args)
}

@PostConstruct
fun init() {
    TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
}

