package com.edu.Institiute.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Payment")

public class Payment {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="paymentSerialID")
    private String paymentSerialID;

    @Column(name="hospitalCharge")
    private Integer hospitalCharge;

    @Column(name="doctorCharge")
    private Integer doctorCharge;

    @Column(name="tax")
    private Integer tax;

    @Column(name="amount")
    private Integer amount;

    @Column(name="paymentMethod")
    private String paymentMethod;

    @Column(name="paymentDate")
    private Date paymentDate;

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

    @ManyToOne
    @JoinColumn(name="appointment_id", referencedColumnName = "id")
    private Appointment appointment;

}
