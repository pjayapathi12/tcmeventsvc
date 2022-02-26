package com.cloudathon.cloudathondemo;

import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowFactory;
import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowInterface;
import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class TcmEventDispatcher {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TcmEventFlowFactory tcmEventFlowFactory;

    public void processEvent(TcmEvent tcmEvent) throws IOException {
        log.info("received {}", tcmEvent);

        TcmEventFlowInterface flowService = null;

        String eventType = tcmEvent.getEventType();
        try {
            flowService = tcmEventFlowFactory.getEventflow(eventType + "MiniflowService");
        } catch (NoSuchBeanDefinitionException e) {
            log.info("no eventflow confgiured for event type ==> {}", eventType);
            return; // don't for now

        }
        flowService.process(tcmEvent);
    }
}
