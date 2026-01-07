package com.example.spring_data_jpa_demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
//@Order(1)
public class PerformanceAspect {

    @Around("execution(* com.example.spring_data_jpa_demo.services.*.*(..))")
    public Object measureTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        /*
        That “next element” could be:
            Another aspect
            Or finally the real target method
         */
        Object result = pjp.proceed(); // calls the next element in the AOP interceptor chain.

        long end = System.currentTimeMillis();

        long time = end - start;

        System.out.println("Time : " + pjp.getSignature().getName() + " took " + time + " ms");

        return result;
    }
}

/*
## When You SHOULD Use @Order (Rule of Thumb)

Use @Order when:

- One aspect depends on another
- One aspect controls execution (security, tx, retry)
- One aspect can stop execution

Don’t use @Order when:

- Aspects are independent
- It’s just logging or metrics


@Around advice is always the outermost wrapper, so it starts first and finishes last.
 */
