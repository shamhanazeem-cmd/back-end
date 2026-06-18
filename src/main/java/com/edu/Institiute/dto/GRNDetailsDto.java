package com.edu.Institiute.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GRNDetailsDto {
    private Integer id;
    private String grn_Item;
    private Integer orderedQty;
    private Integer receivedQty;
    private Integer damagedQty;
    private Integer acceptedQty;

    //@JsonIgnore
    private GRNDto grnHeaderId;
}
