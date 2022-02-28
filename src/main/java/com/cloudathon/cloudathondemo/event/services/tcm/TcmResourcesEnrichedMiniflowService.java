package com.cloudathon.cloudathondemo.event.services.tcm;

import com.cloudathon.cloudathondemo.TcmEventProducer;
import com.cloudathon.cloudathondemo.event.interfaces.TcmEventFlowInterface;
import com.cloudathon.cloudathondemo.models.TcmEvent;
import com.cloudathon.cloudathondemo.persistence.dao.ErrorStatsDao;
import com.cloudathon.cloudathondemo.persistence.dao.TCMDao;
import com.cloudathon.cloudathondemo.persistence.entity.ErrorStats;
import com.cloudathon.cloudathondemo.persistence.entity.TCM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service("TcmResourcesEnrichedMiniflowService")
public class TcmResourcesEnrichedMiniflowService implements TcmEventFlowInterface {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TCMDao tcmDao;
    @Autowired
    private ErrorStatsDao errorStatsDao;

    @Autowired
    private TcmEventProducer producer;


    public void process(TcmEvent tcmEvent) throws JsonProcessingException {
        log.info("received {}", tcmEvent);

        // 1. save data to db
        TCM tcm = mapTcmEventToTcm(tcmEvent);
        if (tcm == null) {
            log.info("tcm is null, dropping event");
            return;
        }
        tcmDao.save(tcm);

        List<ErrorStats> errorStats = mapTcmEventToErrorStats(tcmEvent);
        if (errorStats == null) {
            log.info("errorstats is null, dropping event");
            return;
        }
        errorStats.forEach(x -> {
            errorStatsDao.save(x);
        });

        // 2. publish TcmResourcesDbSaved event to bus
        tcmEvent.setEventType("TcmResourcesDbSaved");
        producer.produce(objectMapper.writeValueAsString(tcmEvent));


    }

    private List<ErrorStats> mapTcmEventToErrorStats(TcmEvent tcmEvent) {
        if (tcmEvent == null || tcmEvent.getResources() == null || tcmEvent.getResources().isEmpty())
            return null;
        List<ErrorStats> errorStatsList = new ArrayList<>();
        tcmEvent.getResources()
                .stream().filter(x -> x != null)
                .forEach(x -> {
                    x.getErrorStats().stream().filter(xx -> xx != null)
                            .forEach(xx -> {
                                ErrorStats errorStats = new ErrorStats();
                                errorStats.setErrorName(xx.getErrorName());
                                errorStats.setErrorType(xx.getErrorType());
                                errorStats.setTcm(tcmEvent.getTcmId());
                                errorStats.setResourceName(x.getResourceName());
                                try {
                                    errorStats.setData(objectMapper.writeValueAsString(xx.getErrorData()));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                errorStatsList.add(errorStats);
                            });
                });

        return errorStatsList;
    }


    private TCM mapTcmEventToTcm(TcmEvent tcmEvent) {
        TCM tcm = new TCM();
        if (tcmEvent == null)
            return null;
        tcm.setTcm(tcmEvent.getTcmId());
        tcm.setEonId(tcmEvent.getEonId());
        tcm.setSubmitterId(tcmEvent.getSubmitter());
        return tcm;
    }

}
