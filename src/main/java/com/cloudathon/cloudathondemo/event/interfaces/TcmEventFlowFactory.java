package com.cloudathon.cloudathondemo.event.interfaces;

import org.springframework.stereotype.Service;

@Service("tcmEventFlowFactory")
public interface TcmEventFlowFactory {
    public TcmEventFlowInterface getEventflow(String flowName);
}
