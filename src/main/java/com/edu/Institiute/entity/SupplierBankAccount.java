package com.edu.Institiute.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supplier_bank_account")
public class SupplierBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Integer bankAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "bank_name", length = 150)
    private String bankName;

    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "account_name", length = 150)
    private String accountName;

    @Column(name = "iban", length = 50)
    private String iban;

    @Column(name = "swift_code", length = 20)
    private String swiftCode;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Builder.Default
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = Boolean.FALSE;

}
