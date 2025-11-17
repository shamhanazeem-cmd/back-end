package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDto {
    private Integer id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String slotDuration;
    private String maxPatients;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private DoctorDto doctor;
}
