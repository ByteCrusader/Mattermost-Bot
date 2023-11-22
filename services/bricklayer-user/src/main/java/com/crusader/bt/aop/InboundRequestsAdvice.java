package com.crusader.bt.aop;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Logging all inbound requests
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InboundRequestsAdvice {

    private static final String UNDEFINED = "unknown";

    /**
     * Advice for logging inbound requests
     */
    @Around(
            value = "within(com.crusader.bt.controller..*)",
            argNames = "joinPoint"
    )
    public Object aroundRestControllersAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodSignature = getRequestParams(joinPoint);

        log.info("Catch inbound request to user service: {}", methodSignature);

        Object response = joinPoint.proceed();
        if (response instanceof Mono<?> out) {
            return out
                    .doOnSuccess(resp ->
                            log.info("Send outbound response from user service")
                    )
                    .doOnError(e ->
                            log.error(
                                    "Catch Exception while process inbound request into user service: {} - {}",
                                    methodSignature,
                                    e
                            )
                    );
        } else if (response instanceof Flux<?> out) {
            return out;
        } else {
            return response;
        }
    }

    /**
     * Build signature and name of inbound name
     */
    @SneakyThrows
    private String getRequestParams(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestMapping annotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);

        String restSignature = Optional.ofNullable(annotation)
                .map(RequestMapping::method)
                .stream()
                .flatMap(Arrays::stream)
                .map(Enum::name)
                .findFirst()
                .orElse(UNDEFINED);

        String methodName = StringUtils.defaultIfBlank(
                signature.getName(),
                UNDEFINED
        );

        return String.format("%s_%s", restSignature, methodName);
    }

}
