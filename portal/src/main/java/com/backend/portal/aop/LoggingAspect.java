package com.backend.portal.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.util.*;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.backend.portal.controller..*(..))")
    public Object logRequestResponseJson(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        Map<String, Object> logMap = new LinkedHashMap<>();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logMap.put("method", request.getMethod());
            logMap.put("uri", request.getRequestURI());



            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
//            while (headerNames.hasMoreElements()) {
//                String header = headerNames.nextElement();
//                headers.put(header, request.getHeader(header));
//            }
//            logMap.put("headers", headers);

            logMap.put("arguments", joinPoint.getArgs());
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
            logMap.put("exception", t.getMessage());
            logger.error("Request failed: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap), t);
            throw t;
        }

        if (result instanceof ResponseEntity<?> responseEntity) {
            logMap.put("status", responseEntity.getStatusCode());
            logMap.put("response", responseEntity.getBody());
        } else {
            logMap.put("response", result);
        }

        logger.info("Request & Response log:\n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap));
        return result;
    }
}

