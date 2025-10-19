package com.edu.Institiute.dto.responseDto;


import com.edu.Institiute.entity.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private int id;
    private String fullName;
    private String nic;
    private String dob;
    private String gender;
    private String address;
    private String contactNo;
    private String email;
    private MedicalHistory medicalHistory;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
}
