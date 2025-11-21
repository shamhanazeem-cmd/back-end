package com.edu.Institiute.dto.responseDto;


import com.edu.Institiute.dto.AppointmentDto;
import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.PatientDto;
import com.edu.Institiute.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PrescriptionResponseDto {

    private Integer id;
    private String prescriptionDate;
    private String notes;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private DoctorDto doctor;
    private PatientDto patient;
    private AppointmentDto appointment;
    private StatusDto status;
}
