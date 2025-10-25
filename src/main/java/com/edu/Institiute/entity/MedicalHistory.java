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
@Table(name = "MedicalHistory")
public class MedicalHistory{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="allergies")
    private String allergies;

    @Column(name="pastSurgeries")
    private String pastSurgeries;

    @Column(name="chronicConditions")
    private String chronicConditions;

    @Column(name="medicalHistory")
    private String medicalHistory;

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


}


