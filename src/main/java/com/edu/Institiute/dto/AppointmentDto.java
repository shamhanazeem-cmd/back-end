package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Integer id;
    private String appointmentDate;
    private String appointmentTime;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private DoctorDto doctor;
    private PatientDto patient;
    private ScheduleDto schedule;
    private StatusDto status;

}
