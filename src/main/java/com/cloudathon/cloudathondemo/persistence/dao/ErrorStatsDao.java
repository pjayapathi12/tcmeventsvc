package com.cloudathon.cloudathondemo.persistence.dao;

import com.cloudathon.cloudathondemo.persistence.entity.ErrorStats;
import com.cloudathon.cloudathondemo.persistence.entity.ErrorStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ErrorStatsDao extends JpaRepository<ErrorStats, ErrorStatsId> {

    @Query("SELECT e FROM ErrorStats e WHERE e.tcm = ?1 and e.resourceName = ?2")
    public List<ErrorStats> fetchErrorStatsByTCMAndResource(String tcm,String resourceName);
}
