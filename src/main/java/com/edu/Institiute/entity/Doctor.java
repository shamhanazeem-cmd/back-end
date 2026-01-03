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
@Table(name = "Doctor")

public class Doctor {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="doctorSerialID")
    private String doctorSerialID;

    @Column(name="doctorName")
    private String doctorName;

    @Column(name="qualifications")
    private String qualifications;

    @Column(name="contactDetails")
    private String contactDetails;

    @Column(name="mail")
    private String mail;

    @Column(name="roomNo")
    private String roomNo;

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
    @JoinColumn(name="specialization_id", referencedColumnName = "id")
    private Specialization specializations;

}
