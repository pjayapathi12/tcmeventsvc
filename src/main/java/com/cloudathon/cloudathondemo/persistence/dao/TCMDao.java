package com.cloudathon.cloudathondemo.persistence.dao;

import com.cloudathon.cloudathondemo.persistence.entity.TCM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCMDao extends JpaRepository<TCM, String> {

    public TCM findByTcm(String tcm);

}
