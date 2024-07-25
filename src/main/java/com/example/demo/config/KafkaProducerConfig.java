package com.example.demo.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory(
        final @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapAddress
    ) {
        return new DefaultKafkaProducerFactory<>(new HashMap<>(){{
            put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        }});
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(final ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}