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
@EnableConfigurationProperties(KafkaProducerProperties.class)
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaProducerProperties kafkaProducerProperties;
    @Autowired
    KafkaTemplate<String, ApiMetric> kafkaTemplate;

    public void send(String msg) {
        log.info("SWE topic = "+kafkaProducerProperties.getTopicName());
        ApiMetric apiMetric = ApiMetric.builder().path("totoPath").timestamp(Timestamp.from(Instant.now())).build();
        log.info("SWE toto");
        kafkaTemplate.send(kafkaProducerProperties.getTopicName(),apiMetric);
    }
}
