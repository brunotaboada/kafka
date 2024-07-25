package com.example.demo;

import jakarta.inject.Inject;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.externaltask.ExternalTaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@EmbeddedKafka(
	ports = 63501,
	partitions = 1,
	controlledShutdown = true,
	topics = { "demo" }
)
class DemoApplicationTests {

	@Inject
	private KafkaTemplate<String, String> kafkaTemplate;

	@Inject
	private ProcessEngine processEngine;

	@Test
	void testSendReceive() throws InterruptedException {
		final String msg = "test-value";
		final String topic = "demo";
		final String msgId = UUID.randomUUID().toString();
		kafkaTemplate.send(MessageBuilder
			.createMessage(msg, new MessageHeaders(Map.of(
					KafkaHeaders.TOPIC, topic,
					"uuid", msgId
				))
			));
		ExternalTaskQuery query = processEngine
				.getExternalTaskService()
				.createExternalTaskQuery();
		long val = query
				.active()
				.count();

		while (true){
			if(val == 0)
				break;
			val = query.active().count();
			Thread.sleep(1000);
		}

	}

}
