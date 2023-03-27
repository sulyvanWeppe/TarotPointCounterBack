package com.sulwep7.tarotpointcounterback.kafka.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.producer.api-calls-metrics-topic")
@Setter
@Getter
public class KafkaProducerProperties {
    private String topicName;
    private String bootstrapServers;
    private String groupId;
    private String keySerializer;
    private String valueSerializer;
}
