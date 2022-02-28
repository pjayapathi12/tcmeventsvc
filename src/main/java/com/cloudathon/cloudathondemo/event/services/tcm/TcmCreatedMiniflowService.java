package com.cloudathon.cloudathondemo.event.services.tcm;

import com.cloudathon.cloudathondemo.TcmEventProducer;
import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowInterface;
import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j2
@Service("TcmCreatedMiniflowService")
public class TcmCreatedMiniflowService implements TcmEventFlowInterface {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private TcmEventProducer producer;

    public void process(TcmEvent tcmEvent) throws IOException {
        log.info("received {}", tcmEvent);

        // 1. get data from mock file
        Resource resource = resourceLoader.getResource("classpath:errorstats-1.json");
        try {
            File file = resource.getFile();
            List<TcmEvent.ErrorStat> errorStat = objectMapper.readValue(file, new TypeReference<List<TcmEvent.ErrorStat>>() {
            });
            log.info("error stats retrieved from file ==> {}", errorStat);
            tcmEvent.getResources().forEach(x -> x.setErrorStats(errorStat) ); // set each resource with same errorstat
        } catch (Exception e) {
            log.info("error encountered while trying to open file");
        }

        // 2. publish TcmResourcesEnriched event to bus
        tcmEvent.setEventType("TcmResourcesEnriched");
        producer.produce(objectMapper.writer().writeValueAsString(tcmEvent));

        log.info("processed tcm create event successfully");
    }

}
