package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.SpringTopicSubscription;
import org.camunda.bpm.client.spring.event.SubscriptionInitializedEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EnableKafka
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(SubscriptionInitializedEvent.class)
	public void catchSubscriptionInitEvent(SubscriptionInitializedEvent event) {
		SpringTopicSubscription topicSubscription = event.getSource();

		if (!topicSubscription.isAutoOpen()) {
			topicSubscription.open();
			log.info("Subscription with topic name ‘{}’ has been opened!", topicSubscription.getTopicName());
		}
	}

}
