package com.example.tasks;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@ExternalTaskSubscription("error_handling")
@Slf4j
public class ErrorTaskExample implements ExternalTaskHandler {

    @Override
    public void execute(
        ExternalTask externalTask,
        ExternalTaskService externalTaskService
    ) {
        VariableMap variables = Variables.createVariables();
        variables.put("Variable1", "ABC-" + new Random().nextInt(16));
        variables.put("Variable2", new Random().nextInt(16));
        externalTaskService.handleFailure(externalTask, "","",1,2);
        externalTaskService.complete(externalTask,variables);
        log.info("MessageId: {} external task has been executed with id: {}",
            externalTask.getVariable("uuid"),
            externalTask.getProcessInstanceId()
        );
    }

}