package com.cloudathon.cloudathondemo;

import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan (basePackages = "com.cloudathon.cloudathondemo")
public class CloudathondemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudathondemoApplication.class, args);
    }

    @Bean
    public ObjectMapper getLessStrictObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper;
    }

    @Bean(name = "tcmEventFlowFactory")
    public FactoryBean tcmEventFlowFactory() {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(TcmEventFlowFactory.class);
        return serviceLocatorFactoryBean;
    }
}
