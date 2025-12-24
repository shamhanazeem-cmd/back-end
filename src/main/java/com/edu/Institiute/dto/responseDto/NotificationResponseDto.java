package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.AppointmentDto;
import com.edu.Institiute.dto.StatusDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationResponseDto {
    private Integer id;
    private Date sentDate;
    private String channel;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private AppointmentDto appointment;
    private StatusDto status;
}
