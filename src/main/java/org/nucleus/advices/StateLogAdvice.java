package org.nucleus.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StateLogAdvice {
    private final Logger log = LogManager.getLogger(StateLogAdvice.class);

    @Pointcut("execution(* org.nucleus.service.address.StateTempServiceImpl.*(..))")
    public void tempServiceMethods() {
    }

    @Pointcut("execution(* org.nucleus.service.address.StateServiceImpl.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* org.nucleus.controller.StateMakerController.*(..))")
    public void makerControllerMethods() {
    }

    @Pointcut("execution(* org.nucleus.controller.StateCheckerController.*(..))")
    public void checkerControllerMethods() {
    }

    @Before("tempServiceMethods()")
    public void logBeforeExecutingTempService(JoinPoint joinPoint) {
        log.trace("Executing StateTemp service method : {}", joinPoint.getSignature().getName());
    }

    @Before("serviceMethods()")
    public void logBeforeExecutingService(JoinPoint joinpoint) {
        log.trace("Executing State service method: {}", joinpoint.getSignature().getName());
    }

    @Before("makerControllerMethods()")
    public void logBeforeExecutingMakerController(JoinPoint joinPoint) {
        log.trace("Executing State maker controller method: {}", joinPoint.getSignature().getName());
    }

    @Before("checkerControllerMethods()")
    public void logBeforeExecutingCheckerControllerMethods(JoinPoint joinpoint) {
        log.trace("Executing State checker controller method: {}", joinpoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "tempServiceMethods()", returning = "result")
    public void logAfterReturningTempServiceMethods(Object result) {
        log.debug("State Temp service method execution completed. Returning: {}", result);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturningServiceMethods(Object result) {
        log.debug("State service method execution completed. Returning: {}", result);
    }

    @AfterReturning(pointcut = "makerControllerMethods()", returning = "result")
    public void logAfterReturningMakerControllerMethods(Object result) {
        log.debug("State maker controller method execution completed. Returning: {}", result);
    }

    @AfterReturning(pointcut = "checkerControllerMethods()", returning = "result")
    public void logAfterReturningCheckerControllerMethods(Object result) {
        log.debug("State checker controller method execution completed. Returning: {}", result);
    }

    @AfterThrowing(pointcut = "tempServiceMethods()", throwing = "exception")
    public void logAfterThrowingTempServiceMethods(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred in State Temp Service method : {}", joinPoint.getSignature().getName(), exception);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowingServiceMethods(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred in State Service method: {}", joinPoint.getSignature().getName(), exception);
    }

}