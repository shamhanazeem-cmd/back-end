package com.edu.Institiute.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorDto {
    private int id;
    private String doctorName;
    private String contactDetails;
    private String mail;
    private String roomNo;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private StatusDto status;
    private SpecializationDto specializations;

}
