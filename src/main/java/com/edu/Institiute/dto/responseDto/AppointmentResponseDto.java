package com.edu.Institiute.dto.responseDto;
import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.PatientDto;
import com.edu.Institiute.dto.ScheduleDto;
import com.edu.Institiute.dto.StatusDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
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
