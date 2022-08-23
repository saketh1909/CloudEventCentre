package com.cmpe275.term.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Order(0)
@Aspect
@Component
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable
     */

    @Around("execution(public * com.cmpe275.term.service.*.*(..))")
    public Object retryAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.printf("Retry aspect prior to the execution of the method %s\n", joinPoint.getSignature().getName());
        int MAX_TRIES = 4;
        for(int i = 1; i <= MAX_TRIES; i++){
            try {
                return joinPoint.proceed();
            } catch (IOException e){
                if(i == MAX_TRIES){
                    System.out.println("Attempt " + i + " failed : No more retries");
                    throw new IOException();
                }
                System.out.println("Attempt " + i + " failed : retrying again");
            }
        }
        return null;
    }

}
