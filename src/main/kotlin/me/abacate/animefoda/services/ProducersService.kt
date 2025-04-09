package me.abacate.animefoda.services

import me.abacate.animefoda.models.Producer
import me.abacate.animefoda.repositories.ProducersRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProducersService(
    private val producerRepository: ProducersRepository
) {
    fun create(name:String): Producer {
        val producer = Producer(name = name)
        return producerRepository.save(producer)
    }
    
//    fun getIdsFromNames(names: List<String>): List<UUID> {
//        val ids = ArrayList<UUID>()
//        for (name in names) {
//            val producer = producerRepository.findByName(name)
//            if(!producer.isPresent) {
//                val p = create(name)
//                ids.add(p.id)
//                continue
//            }
//            ids.add(producer.get().id)
//        }
//        return ids
//    }
}
