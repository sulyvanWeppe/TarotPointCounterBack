package com.sulwep7.tarotpointcounterback.annotation.metrics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulwep7.metricscommon.model.ApiMetric;
import com.sulwep7.tarotpointcounterback.kafka.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class EnableApiMetricAspect {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    KafkaProducer kafkaProducer;

    @Around("@annotation(EnableApiMetric)")
    public Object publishApiMetric(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ApiMetric apiMetric = new ApiMetric();

        Object[] args = proceedingJoinPoint.getArgs();

        for(int i=0; i<args.length; i++) {
            if(args[i] instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest)args[i];
                apiMetric.setApiServerName(httpServletRequest.getServerName());
                apiMetric.setApiServerPort(String.valueOf(httpServletRequest.getServerPort()));
                apiMetric.setRequestURI(httpServletRequest.getRequestURI());
                apiMetric.setTimestamp(Timestamp.from(Instant.now()));
            }
            else {
                apiMetric.setJsonRequestPayload(objectMapper.writeValueAsString(args[i]));
            }
        }

        Object result = proceedingJoinPoint.proceed();

        apiMetric.setJsonResponsePayload(objectMapper.writeValueAsString(result));

        kafkaProducer.send(apiMetric);

        return result;
    }
}
