package com.edu.Institiute.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RFQDetailsDto {
    private int id;
    private String item;
    private int quantity;
    private String remarks;

    //@JsonIgnore
    private RFQHeaderDto rfqHeader;
}
