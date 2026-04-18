package com.edu.Institiute.dto;


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
    private RFQHeaderDto rfqHeader;
}
