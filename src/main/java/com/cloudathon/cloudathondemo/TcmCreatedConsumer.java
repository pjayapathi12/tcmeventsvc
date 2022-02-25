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
public class TcmCreatedConsumer {

  @Autowired
  private ObjectMapper objectMapper;

  @KafkaListener(topics = "#{'${io.confluent.developer.config.topic.name}'}", id = "tcmCreated", clientIdPrefix = "tcmCreatedEventListener")
  public void consume(final ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
    log.info("received {} {}", consumerRecord.key(), consumerRecord.value());
    TcmEvent tcmEvent = objectMapper.readValue(consumerRecord.value(), TcmEvent.class);

    log.info("got tcm event from kafka");
    if (tcmEvent.getEventType() == null || ! tcmEvent.getEventType().equalsIgnoreCase("TcmCreated")) {
      log.info("not interested in event as it's not TcmCreated");
      return;
    }

  }
}
