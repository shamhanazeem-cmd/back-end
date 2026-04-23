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
@Table(name = "SupplierQuotationHeader")
public class SupplierQuotationHeader {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="quotationNumber")
    private String quotationNumber;

    @Column(name="supplier")
    private String supplier;

    @Column(name="date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "rfq_id" , referencedColumnName ="id")
    private RFQHeader rfq;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "quotationHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierQuotationDetail> S_details = new ArrayList<>() ;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;
}
