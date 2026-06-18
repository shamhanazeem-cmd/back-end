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
@Table(name = "purchase_order_Details")
public class PurchaseOrderDetails {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PO_Item")
    private String poItem;

    @Column(name = "orderedQuantity")
    private Integer orderedQuantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "total")
    private Double total; // Calculated in Service

    @ManyToOne
    @JoinColumn(name = "po_id")
    @JsonIgnore // Prevents recursion in JSON
    private PurchaseOrderHeaader PO_Header;
}
