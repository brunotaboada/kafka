package com.example.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory(
        final @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress
    ) {
        return new DefaultKafkaConsumerFactory<>(new HashMap<>(){{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(GROUP_ID_CONFIG, "groupId");
            put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        }});
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(final ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}