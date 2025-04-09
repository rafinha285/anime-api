package me.abacate.animefoda.services

import me.abacate.animefoda.models.Creator
import me.abacate.animefoda.repositories.CreatorsRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreatorsService(
    private val creatorsRepository: CreatorsRepository,
) {
    fun create(name:String): Creator {
        val producer = Creator(name = name)
        return creatorsRepository.save(producer)
    }
    
//    fun getIdsFromNames(names: List<String>): List<UUID> {
//        val ids = ArrayList<UUID>()
//        for (name in names) {
//            val producer = creatorsRepository.findByName(name)
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

