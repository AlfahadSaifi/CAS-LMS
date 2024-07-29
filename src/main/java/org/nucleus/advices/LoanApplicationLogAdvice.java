package org.nucleus.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/** Developer : Monu Kumar*/
@Aspect
@Component
public class LoanApplicationLogAdvice {
    private final Logger logger = LogManager.getLogger(LoanApplicationLogAdvice.class);
    @Pointcut("execution(* org.nucleus.service.LoanApplicationServiceImpl.*(..))")
    public void loanApplicationServiceMethods() {}
    @Before("loanApplicationServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.trace("Executing LoanApplication Service method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "loanApplicationServiceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in LoanApplication Service method: {}", joinPoint.getSignature().getName(), exception);
    }
    @AfterReturning(pointcut = "loanApplicationServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("LoanApplication Service Method: {} returned successfully.", joinPoint.getSignature().getName());
        logger.info("Returned value: {}", result);
    }
}
