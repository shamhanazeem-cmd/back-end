package com.edu.Institiute.dto.responseDto;
import com.edu.Institiute.dto.SupplierDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierAddressResponseDTO {
    private Integer addressId;
    private SupplierDTO supplier;
    private String addressType;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private Boolean isPrimary;
}
