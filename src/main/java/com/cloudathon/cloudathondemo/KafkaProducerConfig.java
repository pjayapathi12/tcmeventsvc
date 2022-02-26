package com.cloudathon.cloudathondemo;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapAddress;


    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN" );
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "myclientid");
        configProps.put(ProducerConfig.ACKS_CONFIG,"all");
        configProps.put("connections.max.idle.ms", "60000");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG,"org.apache.kafka.common.security.plain.PlainLoginModule   required username='GIWKRTNEHMYH6RR5'   password='Xk6alvcrAC8Sk5SsdcMgpofdKixxqogXMgWYvzrRmfdStKQ9qELdrxBm1sAbCtc9';" );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean("kafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
