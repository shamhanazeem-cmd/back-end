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

public class PaymentResponseDto {
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
