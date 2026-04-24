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
@Table(name = "purchase_order_heaader")
public class PurchaseOrderHeaader {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="poNumber")
    private String poNumber;

    @Column(name="poSupplier")
    private String poSupplier;

    @Column(name="poDate")
    private Date poDate;

    @Column(name="expectedDate")
    private Date expectedDate;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;

    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "PO_Header", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderDetails> PO_details = new ArrayList<>();
}
