package net.employeemanager.ems_backend.aspect;

import net.employeemanager.ems_backend.dto.EmployeeDto;
import net.employeemanager.ems_backend.entity.AuditLog;
import net.employeemanager.ems_backend.repository.AuditLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditLoggingAspect.class);

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLoggingAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @AfterReturning(pointcut = "execution(* net.employeemanager.ems_backend.service.EmployeeService.update*(..))", returning = "result")
    public void afterUpdate(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        Long employeeId = extractEmployeeId(joinPoint, result);
        saveAudit(action, employeeId);
    }

    @AfterReturning(pointcut = "execution(* net.employeemanager.ems_backend.service.EmployeeService.delete*(..))")
    public void afterDelete(JoinPoint joinPoint) {
        String action = joinPoint.getSignature().getName();
        Long employeeId = extractEmployeeId(joinPoint, null);
        saveAudit(action, employeeId);
    }

    private Long extractEmployeeId(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object first = args[0];
            if (first instanceof Long)
                return (Long) first;
            if (first instanceof Number)
                return ((Number) first).longValue();
        }
        if (result instanceof EmployeeDto) {
            return ((EmployeeDto) result).getId();
        }
        return null;
    }

    private void saveAudit(String action, Long employeeId) {
        AuditLog audit = new AuditLog();
        audit.setAction(action);
        audit.setEmployeeId(employeeId);
        audit.setTimestamp(LocalDateTime.now());
        // TODO: Replace with actual authenticated user once security is integrated
        audit.setChangedBy("system");
        try {
            auditLogRepository.save(audit);
        } catch (Exception e) {
            log.error("Failed to save audit log for action {} empId {}: {}", action, employeeId, e.getMessage());
        }
    }
}