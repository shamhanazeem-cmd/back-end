package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.PrescriptionDto;
import com.edu.Institiute.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicationResponseDto {

    private Integer id;
    private String drugName;
    private String dosage;
    private String duration;
    private String instructions;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private StatusDto status;
    private PrescriptionDto prescription;
}
