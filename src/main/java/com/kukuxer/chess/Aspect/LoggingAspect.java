package com.kukuxer.chess.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    String red = "\u001B[38;5;124m";
    String green = "\u001B[38;5;34m";
    String reset = "\u001B[0m";
    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.kukuxer.chess.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* com.kukuxer.chess.service.*.*(..))")
    private void forServicePackage() {
    }


    @Pointcut("forControllerPackage() || forServicePackage()")
    private void ForControllerAndService() {
    }

    @AfterReturning(
            pointcut = "ForControllerAndService()",
            returning = "result"
    )
    public void AfterReturning(JoinPoint joinPoint, Object result){
        String method = joinPoint.getSignature().toShortString();
        logger.info(green + "@After: calling method  " + method + reset);


        logger.info("The result: " + result);
    }
}

