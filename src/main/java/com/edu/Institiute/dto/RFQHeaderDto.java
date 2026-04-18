package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RFQHeaderDto {
    private Integer id;
    private String rfqNumber;
    private Date requestDate;
    private String requestedBy;
    private Date requiredDate;
    private List<RFQDetailsDto> details;
    private StatusDto status;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
}
