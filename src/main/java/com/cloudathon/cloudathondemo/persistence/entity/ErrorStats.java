package com.cloudathon.cloudathondemo.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Clob;

@Entity
@Table(name = "ERRORSTATS")
@IdClass(ErrorStatsId.class)
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStats {

    @Id
    @Column(name = "ERROR_NAME")
    String errorName;

    @Id
    @Column(name = "TCM")
    String tcm;

    @Id
    @Column(name = "RESOURCE_NAME")
    String resourceName;

    @Id
    @Column(name = "ERROR_TYPE")
    String errorType;

    @Column(name = "JIRA")
    String jira;

    @Column(name = "JIRA_STATUS")
    String jiraStatus;

    @Lob
    @Column(name = "DATA")
    String data;
}
