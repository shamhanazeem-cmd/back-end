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
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="fullName")
    private String fullName;

    @Column(name="nic")
    private String nic;

    @Column(name="dob")
    private String dob;

    @Column(name="gender")
    private String gender;

    @Column(name="address")
    private String address;

    @Column(name="contactNo")
    private String contactNo;

    @Column(name="email")
    private String email;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;


    @ManyToOne
    @JoinColumn(name = "medicalhistory_id",  referencedColumnName = "id")
    private MedicalHistory medicalHistory;

    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;
}
