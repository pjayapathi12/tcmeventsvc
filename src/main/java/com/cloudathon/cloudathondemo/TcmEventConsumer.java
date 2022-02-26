package com.cloudathon.cloudathondemo;

import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class TcmEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TcmEventDispatcher dispatcher;

    @KafkaListener(topics = "#{'${io.confluent.developer.config.topic.name}'}", id = "tcmEvents", clientIdPrefix = "tcmEventListener")
    public void consume(final ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        log.info("received {} {}", consumerRecord.key(), consumerRecord.value());
        TcmEvent tcmEvent = objectMapper.readValue(consumerRecord.value(), TcmEvent.class);

        dispatcher.processEvent(tcmEvent);

    }
}
