package com.cloudathon.cloudathondemo;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TcmEventProducer {

    @Autowired
    private  KafkaTemplate<String, String> producer;
    @Value("${io.confluent.developer.config.topic.name}")
    private String topic;


    public String produce(String event) {
        // Produce sample data
        final String key = "alice";
        //ProducerRecord<String, String> record = new ProducerRecord<>(key, event);
        log.info("Producing record: {}\t{}", key, event);
        producer.send(topic, key, event).addCallback(
                result -> {
                    final RecordMetadata m;
                    if (result != null) {
                        m = result.getRecordMetadata();
                        log.info("Produced record to topic {} partition {} @ offset {}",
                                m.topic(),
                                m.partition(),
                                m.offset());
                    }
                },
                exception -> log.error("Failed to produce to kafka", exception));

        producer.flush();
        log.info("message was produced to topic {}", topic);
        return "success";
    }

}
