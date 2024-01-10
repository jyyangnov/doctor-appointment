package com.yan.daserver.entity;

import lombok.Data;

@Data
public class Appointment {
    private Integer id;
    private Integer doctorId;
    private Long startTime;
    private Long endTime;
    private Integer patientId;
    private Integer status;
}
