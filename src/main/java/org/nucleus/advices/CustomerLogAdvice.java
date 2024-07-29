package org.nucleus.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/** Developer : Monu Kumar*/
@Aspect
@Component
public class CustomerLogAdvice {
    private final Logger logger = LogManager.getLogger(CustomerLogAdvice.class);
    @Pointcut("execution(* org.nucleus.service.CustomerServiceImpl.*(..))")
    public void customerServiceMethods() {}
    @Before("customerServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.trace("Executing Customer Service method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "customerServiceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in Customer Service method: {}", joinPoint.getSignature().getName(), exception);
    }
    @AfterReturning(pointcut = "customerServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Customer Service Method: {} returned successfully.", joinPoint.getSignature().getName());
        logger.info("Returned value: {}", result);
    }
}
