package com.edu.Institiute.dto;


import com.edu.Institiute.entity.Appointment;
import com.edu.Institiute.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationDto {
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
