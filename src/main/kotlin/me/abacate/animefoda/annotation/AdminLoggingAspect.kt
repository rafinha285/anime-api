package me.abacate.animefoda.annotation

import me.abacate.animefoda.services.UserAdminLogService
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
        val parameters = method.parameters.map {it.name}
        val args = joinPoint.args
        
        var result = template
        parameters.forEachIndexed { index, name ->
            result = result.replace("{$name}", args[index].toString())
        }
        return result
    }
}