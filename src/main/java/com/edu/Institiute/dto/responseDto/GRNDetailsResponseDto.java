package com.edu.Institiute.dto.responseDto;
import com.edu.Institiute.dto.GRNDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GRNDetailsResponseDto {
    private Integer id;
    private String grn_Item;
    private Integer orderedQty;
    private Integer receivedQty;
    private Integer damagedQty;
    private Integer acceptedQty;

    //@JsonIgnore
    private GRNDto grnHeaderId;
}
