package com.sulwep7.tarotpointcounterback.kafka.producer;

import com.sulwep7.metricscommon.model.ApiMetric;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@Slf4j
@EnableConfigurationProperties(KafkaProducerApiMetricsProperties.class)
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaProducerApiMetricsProperties kafkaProducerApiMetricsProperties;
    @Autowired
    KafkaTemplate<String, ApiMetric> kafkaTemplate;

    public void send(ApiMetric apiMetric) {
        kafkaTemplate.send(kafkaProducerApiMetricsProperties.getTopicName(),apiMetric);
    }
}
