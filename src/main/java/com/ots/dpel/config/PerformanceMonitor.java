package com.ots.dpel.config;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.global.utils.UserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PerformanceMonitor {
    
    @Pointcut("execution(* com.ots.dpel.*.services.*.*(..))")
    private void allServiceOperation() {
    }
    
    @Pointcut("execution(* com.ots.dpel.*.controllers.*.*(..))")
    private void allControllerOperation() {
    }
    
    @Around("allServiceOperation() || allControllerOperation()")
    public Object controllerOperationMonitor(ProceedingJoinPoint joinPoint_p) throws Throwable {
        
        // DpUserDetailsDTO currentCrUser = UserUtils.getUser();
        //
        // Monitor monitor = null;
        //
        // try {
        //     StringBuffer monitorName = new StringBuffer();
        //     monitorName.append(currentCrUser == null ? "" : currentCrUser.getRegisterOfficeName() +
        //         "/" + currentCrUser.getMunicipalRegistryName() + ": ").
        //         append(joinPoint_p.getSignature().getDeclaringTypeName()).append(".").append(joinPoint_p.getSignature().getName());
        //     monitor = MonitorFactory.start(monitorName.toString());
        //
        //     return joinPoint_p.proceed();
        // } catch (Exception e) {
        //     throw e;
        // } finally {
        //     monitor.stop();
        // }
    
        return joinPoint_p.proceed();
        
    }
    
}
