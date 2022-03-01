package com.cloudathon.cloudathondemo.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStatsId implements Serializable {
    String errorName;
    String tcm;
    String resourceName;
    String errorType;
}
