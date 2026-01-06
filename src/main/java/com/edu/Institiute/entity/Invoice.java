package com.edu.Institiute.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Invoice")
public class Invoice {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="invoiceNumber")
    private String invoiceNumber;

    @Column(name="issuedDate")
    private Date issuedDate;

    @Column(name="totalAmount")
    private Integer totalAmount;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;


    @ManyToOne
    @JoinColumn(name="payment_Id", referencedColumnName = "id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;


}
