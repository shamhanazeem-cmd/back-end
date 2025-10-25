package com.edu.Institiute.dto.requestDto;

import com.edu.Institiute.entity.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class RequestRegistryDto {

    private String studentCode;
    private String studentName;
    private String studentAge;
    private String studentNic;
    private Integer status;

    private String courseCode;
    private String courseName;

    private String allergies;
    private String pastSurgeries;
    private String chronicConditions;
    private String medicalHistory;


    private String fullName;
    private String nic;
    private String dob;
    private String gender;
    private String address;
    private String contactNo;
    private String email;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private Integer patientMedicalHistory;

}
