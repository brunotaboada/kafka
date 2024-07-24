package com.example.listerner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

@Component
public class Listener {

    @KafkaListener(topics = "${app.topic.name}")
    public void listenWithHeaders(
        @Payload String message,
        @Header(RECEIVED_PARTITION) int partition
    ) {
        System.out.println("Received Message: " + message + " from partition: " + partition);
    }

}
