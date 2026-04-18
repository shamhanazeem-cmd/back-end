package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.RFQHeaderDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RFQDetailsResponseDto {
    private int id;
    private String item;
    private Integer quantity;
    private String remarks;

    //@JsonIgnore
    private RFQHeaderDto rfqHeader;
}
