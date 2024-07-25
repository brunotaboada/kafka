package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("kafka")
public class Kafka {

    private static final Logger log = LoggerFactory.getLogger(Kafka.class);
    @Value(value = "${app.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Kafka(final KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @GetMapping("/{code}/{value}")
    public void send(
        @PathVariable String value,
        @PathVariable String code
    ) {
        String msgId = UUID.randomUUID().toString();
        kafkaTemplate.send(MessageBuilder
            .createMessage(value + "|" + code, new MessageHeaders(Map.of(
                KafkaHeaders.TOPIC, topic,
            "uuid", msgId
            ))
        ));
        log.info("MessageId: {} sent.", msgId);
    }

}