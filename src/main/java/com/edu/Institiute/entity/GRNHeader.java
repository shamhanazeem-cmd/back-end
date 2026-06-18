package com.edu.Institiute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "grn_header")
public class GRNHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="grnNumber")
    private String grnNumber;

    @Column(name="receivedDate")
    private Date receivedDate;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id" , referencedColumnName ="supplier_id")
    private Supplier grn_Supplier;

    @ManyToOne
    @JoinColumn(name = "po_header_id")
    private PurchaseOrderHeaader purchaseOrder;


    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;


    @OneToMany(mappedBy = "grnHeader", cascade = CascadeType.ALL)
    private List<GRNDetails> grnDetails = new ArrayList<>();

}
