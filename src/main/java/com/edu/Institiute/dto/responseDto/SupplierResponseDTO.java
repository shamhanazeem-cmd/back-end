package com.edu.Institiute.dto.responseDto;


import com.edu.Institiute.dto.PaymentDto;
import com.edu.Institiute.dto.StatusDto;
import com.edu.Institiute.dto.SupplierAddressDTO;
import com.edu.Institiute.dto.SupplierBankAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {
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
