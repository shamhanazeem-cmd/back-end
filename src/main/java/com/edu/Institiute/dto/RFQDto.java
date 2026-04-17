package com.edu.Institiute.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RFQDto {
    private String rfqNumber;
    private Date requestDate;
    private String requestedBy;
    private Date requiredDate;


    // This connects the two files
    private List<RFQDetailsDto> details;
    private StatusDto status;
}
