package me.abacate.animefoda

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import java.util.*

@SpringBootApplication
@EnableCaching
class AnimefodaApplication
fun main(args: Array<String>) {
    runApplication<AnimefodaApplication>(*args)
}

@PostConstruct
fun init() {
    TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
}

