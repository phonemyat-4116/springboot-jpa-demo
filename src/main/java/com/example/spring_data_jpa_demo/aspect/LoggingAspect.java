package com.example.spring_data_jpa_demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.spring_data_jpa_demo.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println("LOG: Calling " + joinPoint.getSignature().getName()
        + " with args " + Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning("execution(* com.example.spring_data_jpa_demo.services.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {

        System.out.println("LOG: Finished " + joinPoint.getSignature().getName());

    }
}
