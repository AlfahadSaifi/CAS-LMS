package org.nucleus.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RescheduleLogAdvice {
    private final Logger logger = LogManager.getLogger(RescheduleLogAdvice.class);
    @Pointcut("execution(* org.nucleus.service.RescheduleServiceImpl.*(..))")
    public void serviceMethods() {}
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.trace("Executing method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {}", joinPoint.getSignature().getName(), exception);
    }
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method: {} returned successfully.", joinPoint.getSignature().getName());
        logger.info("Returned value: {}", result);
    }
}
