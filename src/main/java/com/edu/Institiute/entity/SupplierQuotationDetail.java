package com.edu.Institiute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "SupplierQuotationDetail")
public class SupplierQuotationDetail {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="SQ_item")
    private String SQ_item;

    @Column(name="quotedPrice")
    private Double quotedPrice;

    @Column(name="SQ_quantity")
    private Integer SQ_quantity;

    @Column(name="deliveryDays")
    private Integer deliveryDays;

    @ManyToOne
    @JoinColumn(name = "quotation_header_id")
    @JsonIgnore
    private SupplierQuotationHeader quotationHeader;


}
