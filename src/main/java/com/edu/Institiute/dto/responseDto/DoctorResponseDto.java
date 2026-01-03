package com.edu.Institiute.dto.responseDto;


import com.edu.Institiute.dto.SpecializationDto;
import com.edu.Institiute.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorResponseDto {

    private int id;
    private String doctorSerialID;
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
