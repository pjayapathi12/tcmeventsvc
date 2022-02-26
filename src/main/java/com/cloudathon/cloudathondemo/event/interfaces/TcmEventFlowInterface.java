package com.cloudathon.cloudathondemo.event.interfaces;


import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TcmEventFlowInterface {
    public void process(TcmEvent tcmEvent) throws JsonProcessingException;

}
