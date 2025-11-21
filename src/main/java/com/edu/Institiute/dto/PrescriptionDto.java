package com.edu.Institiute.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PrescriptionDto {

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
