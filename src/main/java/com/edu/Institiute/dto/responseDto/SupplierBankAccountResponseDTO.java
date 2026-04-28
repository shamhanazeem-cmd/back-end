package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierBankAccountResponseDTO {
    private Integer bankAccountId;
    private SupplierDTO supplier;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private String iban;
    private String swiftCode;
    private String currencyCode;
    private Boolean isDefault;
}
