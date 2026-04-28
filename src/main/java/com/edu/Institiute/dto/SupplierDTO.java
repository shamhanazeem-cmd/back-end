package com.edu.Institiute.dto;

import com.edu.Institiute.entity.SupplierAddress;
import com.edu.Institiute.entity.SupplierBankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    private String supplierType;
    private StatusDto status;
    private String currencyCode;
    private PaymentDto paymentTermsId;
    private String taxNumber;
    private Date createdDate;
    private String createdBy;
    private String contactName;
    private String phone;
    List<SupplierAddressDTO> addresses;
    List<SupplierBankAccountDTO> bankAccounts;

}
