package com.cmpe275.term.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class ValidationAspect {


    @Before("execution(public * com.cmpe275.term.service.UserAuthenticationService.userSignIn(..))")
    public void userSignInValidation(JoinPoint joinPoint) {
        System.out.printf("Doing validation prior to the execution of the method %s\n"
                , joinPoint.getSignature().getName());
        String email = (String) joinPoint.getArgs()[0];
        String password = (String) joinPoint.getArgs()[1];

        if(email.length()==0 || email.isEmpty() || password.length()==0 || password.isEmpty()){
            throw new IllegalArgumentException("Exception with Tweet method. Input Validation Failed");
        }
    }


}
