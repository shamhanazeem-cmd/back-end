package com.edu.Institiute.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryResponseDto {
    private int id;
    private String allergies;
    private String pastSurgeries;
    private String chronicConditions;
    private String medicalHistory;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;

}
