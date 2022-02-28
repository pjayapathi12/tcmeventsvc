package com.cloudathon.cloudathondemo;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Service
public class TcmEventProducer {

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;
    @Value("${io.confluent.developer.config.topic.name}")
    private String topic;


    public String produce(String event) {
        // Produce sample data
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, event);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + event +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
                log.info("Unable to send message=["
                        + event + "] due to : " + ex.getMessage());
            }
        });
        kafkaTemplate.flush();
        log.info("message was produced to topic {}", topic);
        return "success";
    }

}
