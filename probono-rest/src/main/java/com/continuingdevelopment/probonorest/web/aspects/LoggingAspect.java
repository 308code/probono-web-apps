package com.continuingdevelopment.probonorest.web.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(annotation)")
    public void logBeforeMethodCall(JoinPoint joinPoint, final LogMethod annotation) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] params = joinPoint.getArgs();
        if (ObjectUtils.isNotEmpty(params)) {
            switch (annotation.level()) {
                case ("INFO"): {
                    log.info("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called with parameters {}.",
                            className, methodName, Arrays.toString(params));
                    break;
                }
                case ("ERROR"): {
                    log.error("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called with parameters {}.",
                            className, methodName, Arrays.toString(params));
                    break;
                }
                default: {
                    log.debug("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called with parameters {}.",
                            className, methodName, Arrays.toString(params));
                    break;
                }
            }
        } else {
            switch (annotation.level()) {
                case ("INFO"): {
                    log.info("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called", className, methodName);
                    break;
                }
                case ("ERROR"): {
                    log.error("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called", className, methodName);
                    break;
                }
                default: {
                    log.debug("PROBONO-APPLICATIONS --> Before:  Class Name: {} Method Name: {} was called", className, methodName);
                    break;
                }
            }
        }
    }

    @AfterReturning(value = "@annotation(annotation)", returning = "result")
    public void logAfterMethodCall(JoinPoint joinPoint, Object result, final LogMethod annotation) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        switch (annotation.level()) {
            case ("INFO"): {
                log.info("PROBONO-APPLICATIONS --> After:  Class Name: {} Method Name: {} completed and returned {}",
                        className, methodName, result);
                break;
            }
            case ("ERROR"): {
                log.error("PROBONO-APPLICATIONS --> After:  Class Name: {} Method Name: {} completed and returned {}",
                        className, methodName, result);
                break;
            }
            default: {
                log.debug("PROBONO-APPLICATIONS --> After:  Class Name: {} Method Name: {} completed and returned {}",
                        className, methodName, result);
                break;
            }
        }
    }

    @Around("@annotation(annotation)")
    public Object executionTime(ProceedingJoinPoint point, final LogMethod annotation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        switch (annotation.level()) {
            case ("INFO"): {
                log.info("PROBONO-APPLICATIONS --> Execution Time:  Class Name: " + point.getSignature().getDeclaringTypeName() +
                        ". Method Name: " + point.getSignature().getName() +
                        ". Time taken for Execution is: " +
                        (endTime - startTime) + "ms");
                break;
            }
            case ("ERROR"): {
                log.error("PROBONO-APPLICATIONS --> Execution Time:  Class Name: " + point.getSignature().getDeclaringTypeName() +
                        ". Method Name: " + point.getSignature().getName() +
                        ". Time taken for Execution is: " +
                        (endTime - startTime) + "ms");
                break;
            }
            default: {
                log.debug("PROBONO-APPLICATIONS --> Execution Time:  Class Name: " + point.getSignature().getDeclaringTypeName() +
                                ". Method Name: " + point.getSignature().getName() +
                                ". Time taken for Execution is: " +
                                (endTime - startTime) + "ms");
                break;
            }
        }
        return object;
    }
}
