package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin kafkaAdmin(
        final @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress
    ) {
        return new KafkaAdmin(Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress));
    }
    
    @Bean
    public NewTopic topic1(final @Value(value = "${app.topic.name}") String topic) {
         return new NewTopic(topic, 1, (short) 1);
    }

}