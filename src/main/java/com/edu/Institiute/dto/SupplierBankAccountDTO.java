package com.edu.Institiute.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierBankAccountDTO {
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
