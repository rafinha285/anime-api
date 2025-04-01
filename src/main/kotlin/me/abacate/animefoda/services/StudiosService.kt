package me.abacate.animefoda.services

import me.abacate.animefoda.models.Studio
import me.abacate.animefoda.repositories.StudiosRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StudiosService (
    private val studiosRepository: StudiosRepository,
) {
    fun create(name:String): Studio {
        val producer = Studio(name = name)
        return studiosRepository.save(producer)
    }
    
    fun getIdsFromNames(names: List<String>): List<UUID> {
        val ids = ArrayList<UUID>()
        for (name in names) {
            val producer = studiosRepository.findByName(name)
            if(!producer.isPresent) {
                val p = create(name)
                ids.add(p.id)
                continue
            }
            ids.add(producer.get().id)
        }
        return ids
    }
}
