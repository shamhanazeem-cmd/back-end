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

public class PaymentDto {

    private Integer id;
    private String paymentSerialID;
    private Integer hospitalCharge;
    private Integer doctorCharge;
    private Integer tax;
    private Integer amount;
    private String paymentMethod;
    private Date paymentDate;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private StatusDto status;
    private AppointmentDto appointment;

}
