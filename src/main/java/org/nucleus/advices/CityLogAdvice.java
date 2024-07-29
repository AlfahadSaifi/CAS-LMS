package org.nucleus.advices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/******UNZALA*****/
@Aspect
@Component
public class CityLogAdvice {
    private final Logger log = LogManager.getLogger(CityLogAdvice.class);


    @Pointcut("execution(* org.nucleus.service.address.CityTempServiceImpl.*(..))")
    public void tempServiceMethods(){}
    @Pointcut("execution(* org.nucleus.service.address.CityServiceImpl.*(..))")
    public void serviceMethods(){}

    @Pointcut("execution(* org.nucleus.controller.CityMakerController.*(..))")
    public void makerControllerMethods(){}
    @Pointcut("execution(* org.nucleus.controller.CityCheckerController.*(..))")
    public void checkerControllerMethods(){}

    @Before("tempServiceMethods()")
    public void logBeforeExecutingTempService(JoinPoint joinPoint){
        log.trace("Executing CityTemp service method : {}", joinPoint.getSignature().getName());
    }
    @Before("serviceMethods()")
    public void logBeforeExecutingService(JoinPoint joinpoint){
        log.trace("Executing City service method: {}" ,joinpoint.getSignature().getName());
    }

    @Before("makerControllerMethods()")
    public void logBeforeExecutingMakerController(JoinPoint joinPoint){
        log.trace("Executing city maker controller method: {}" , joinPoint.getSignature().getName());
    }
    @Before("checkerControllerMethods()")
    public void logBeforeExecutingCheckerControllerMethods(JoinPoint joinpoint){
        log.trace("Executing city checker controller method: {}" ,joinpoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "tempServiceMethods()", returning ="result")
    public void logAfterReturningTempServiceMethods(Object result){
        log.debug("City Temp service method execution completed. Returning: {}", result);
    }

   @AfterReturning(pointcut = "serviceMethods()", returning ="result")
    public void logAfterReturningServiceMethods(Object result){
        log.debug("City service method execution completed. Returning: {}", result);
    }


    @AfterReturning(pointcut = "makerControllerMethods()", returning ="result")
    public void logAfterReturningMakerControllerMethods(Object result){
        log.debug("City maker controller method execution completed. Returning: {}", result);
    }
    @AfterReturning(pointcut = "checkerControllerMethods()", returning ="result")
    public void logAfterReturningCheckerControllerMethods(Object result){
        log.debug("City checker controller method execution completed. Returning: {}", result);
    }

    @AfterThrowing(pointcut="tempServiceMethods()" , throwing = "exception")
    public void logAfterThrowingTempServiceMethods(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred in city Temp Service method : {}", joinPoint.getSignature().getName(),exception);
    }
   @AfterThrowing(pointcut="serviceMethods()", throwing = "exception")
    public void logAfterThrowingServiceMethods(JoinPoint joinPoint, Throwable exception ){
        log.error("Exception occurred in city Service method: {}", joinPoint.getSignature().getName(),exception);
    }

/*    @AfterThrowing(pointcut="makerControllerMethod()" , throwing = "exception")
    public void logAfterThrowingMakerControllerMethods(JoinPoint joinPoint, Throwable exception){
        log.error("Exception occurred in city Maker Controller: {}", joinPoint.getSignature().getName(),exception);
    }
    @AfterThrowing(pointcut="checkerControllerMethod()" , throwing = "exception")
    public void logAfterThrowingCheckerControllerMethods(JoinPoint joinPoint, Throwable exception){
        log.error("Exception occurred in city Checker Controller: {}", joinPoint.getSignature().getName(),exception);
    }*/
}
