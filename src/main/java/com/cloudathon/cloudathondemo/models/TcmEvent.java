package com.cloudathon.cloudathondemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TcmEvent {

    String eventType;
    String tcmId;
    String eonId;
    String submitter;
    String eventTimestamp;
    List<TcmResource> resources;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class TcmResource {

        String resourceType;
        String resourceName;
        List<ErrorStat> errorStats;

    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ErrorStat {

        String errorType;
        String errorDetails;
        List<DailyStat> dailyStats;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class DailyStat {

        int prodCount;
        int qaCount;
        Date date;
    }

}
