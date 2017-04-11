package com.wen.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by wen on 2017/4/10.
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger=LoggerFactory.getLogger(LoggerAspect.class);

    @Before("execution(* com.wen.wenda.controller.*.*(..))")
    public void beforeMethed(JoinPoint joinPoint){
        //logger.info("before Mether: "+joinPoint.getSignature().getName());
    }

    @After("execution(* com.wen.wenda.controller.*.*(..))")
    public void afterMethed(JoinPoint joinPoint){

        //logger.info("after Mether: "+joinPoint.getSignature().getName());
    }
}
