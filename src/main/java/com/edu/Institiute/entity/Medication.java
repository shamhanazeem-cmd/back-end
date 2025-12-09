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
@Table(name = "Medication")

public class Medication {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="drugName")
    private String drugName;

    @Column(name="dosage")
    private String dosage;

    @Column(name="duration")
    private String duration;

    @Column(name="instructions")
    private String instructions;

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
    @JoinColumn(name="prescription_Id", referencedColumnName = "id")
    private Prescription prescription;


}