package me.abacate.animefoda.annotation

import me.abacate.animefoda.entities.user.adminlog.*
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class AdminLoggingAspect(
    private val adminLogService: UserAdminLogService
) {
    @After("@annotation(adminAction)")
    fun logAdminAction(joinPoint: JoinPoint, adminAction: AdminAction) {
        val processedCommand = replacePlaceholders(adminAction.command, joinPoint)
        
        adminLogService.logAdminCommand(processedCommand);
    }
    private fun replacePlaceholders(template: String, joinPoint: JoinPoint): String {
        val method = (joinPoint.signature as MethodSignature).method
        val parameters = method.parameters
        val args = joinPoint.args
        
        var result = template
        val placeholderRegex = "\\{(.*?)\\}".toRegex() // Regex para capturar {param.propriedade}
        
        placeholderRegex.findAll(result).forEach { matchResult ->
            val placeholder = matchResult.groupValues[1] // Ex: "prod.name"
            val parts = placeholder.split(".")
            if (parts.size != 2) return@forEach // Ignora placeholders sem notação de ponto
            
            val paramName = parts[0] // Ex: "prod"
            val propertyName = parts[1] // Ex: "name"
            
            // Encontra o índice do parâmetro pelo nome
            val paramIndex = parameters.indexOfFirst { it.name == paramName }
            if (paramIndex == -1) return@forEach
            
            // Obtém o objeto do parâmetro (ex: AddProducersRequest)
            val paramValue = args[paramIndex]
            
            // Usa reflexão para obter o valor da propriedade
            val propertyValue = try {
                val field = paramValue.javaClass.getDeclaredField(propertyName)
                field.isAccessible = true
                field.get(paramValue).toString()
            } catch (ex: Exception) {
                "N/A" // Valor padrão se a propriedade não existir
            }
            
            // Substitui o placeholder pelo valor
            result = result.replace("{${placeholder}}", propertyValue)
        }
        
        return result
    }
}