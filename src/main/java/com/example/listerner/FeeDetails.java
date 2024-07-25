package com.example.listerner;

import com.example.demo.model.Fee;
import com.example.demo.service.FeeService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

@Component
@Slf4j
public class FeeDetails {

    private final RuntimeService runtimeService;
    private final FeeService feeService;

    public FeeDetails(
        final RuntimeService runtimeService,
        final FeeService feeService) {
        this.runtimeService = runtimeService;
        this.feeService = feeService;
    }

    @KafkaListener(topics = "${app.topic.name}")
    public void listenWithHeaders(
        @Payload String message,
        @Header(RECEIVED_PARTITION) int partition,
        @Header("uuid") String uuid
    ) {
        log.info("MessageId: {} received [{}]", uuid, message);
        log.info("MessageId: {} stored in db.", uuid);//store message in db.

//        String json = "{\"name\":\""+ message +"\"}";
//        SpinJsonNode jsonValue = Spin.JSON(json);
//
//        runtimeService.startProcessInstanceByKey("demo", new HashMap<>(){{
//            put("myJson", jsonValue);
//            put("uuid", uuid);
//        }});
        String[] vals = message.split("\\|");

        feeService.feeUser(Fee
            .builder()
            .code(vals[1])
            .status("PENDING")
            .fee(new BigDecimal(vals[0]))
            .build()
        );

        log.info("Process demo has initiated.");
    }

}
