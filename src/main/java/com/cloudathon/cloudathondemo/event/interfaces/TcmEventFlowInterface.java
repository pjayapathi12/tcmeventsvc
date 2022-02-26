package com.cloudathon.cloudathondemo.event.interfaces;


import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface TcmEventFlowInterface {
    public void process(TcmEvent tcmEvent) throws IOException;

}
