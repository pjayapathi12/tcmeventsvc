package com.cloudathon.cloudathondemo;

import com.cloudathon.cloudathondemo.entity.TCM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TcmResourcesRepository extends JpaRepository<TCM,String> {
}
