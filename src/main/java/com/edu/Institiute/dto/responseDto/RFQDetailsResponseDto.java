package com.edu.Institiute.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class RFQDetailsResponseDto {
    private String item;
    private Integer quantity;
    private String remarks;
}
