package com.example.spring_data_jpa_demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.example.spring_data_jpa_demo.annotations.AdminOnly;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(adminOnly)")
    public Object checkAdmin(ProceedingJoinPoint pjp, AdminOnly adminOnly) throws Throwable{
        boolean isAdmin = false; // simulate auth check

        if (!isAdmin) {
            throw new AccessDeniedException("Access Denied");
        }

        return pjp.proceed();
    }
}
