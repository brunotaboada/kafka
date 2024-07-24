package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class Kafka {

    @Value(value = "${app.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Kafka(final KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @GetMapping("/{msg}")
    public void send(@PathVariable String msg) {
        kafkaTemplate.send(topic, msg);
    }

}