package com.cloudathon.cloudathondemo.event.services.tcm;

import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowInterface;
import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service("TcmUpdatedMiniflowService")
public class TcmUpdatedMiniflowService implements TcmEventFlowInterface {

  @Autowired
  private ObjectMapper objectMapper;

  public void process(TcmEvent tcmEvent) throws JsonProcessingException {
    log.info("received {}", tcmEvent);

    // 1. get data from mock file

    // 2. publish TcmResourcesEnriched event to bus

    }

}
