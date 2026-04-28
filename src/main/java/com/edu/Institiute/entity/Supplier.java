package com.edu.Institiute.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "supplier")
public class Supplier {
    @Id
    @Column(name = "supplier_id", length = 20)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer supplierId;

    @Column(name = "supplier_code", length = 20, nullable = false, unique = true)
    private String supplierCode;

    @Column(name = "supplier_name", length = 200, nullable = false)
    private String supplierName;

    @Column(name = "supplier_type", length = 50)
    private String supplierType;

    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "tax_number", length = 50)
    private String taxNumber;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;

    @Column(name = "contact_name", length = 150, nullable = false)
    private String contactName;

    @Column(name = "phone", length = 30)
    private String phone;

    @ManyToOne
    @JoinColumn(name="payment_terms_id", referencedColumnName = "id")
    private Payment paymentTermsId;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierBankAccount> bankAccounts = new ArrayList<>();
}
