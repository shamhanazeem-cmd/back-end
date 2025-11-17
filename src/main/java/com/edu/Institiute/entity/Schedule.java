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
@Table(name = "Schedule")
public class Schedule {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="dayOfWeek")
    private String dayOfWeek;

    @Column(name="startTime")
    private String startTime;

    @Column(name="endTime")
    private String endTime;

    @Column(name="slotDuration")
    private String slotDuration;

    @Column(name="maxPatients")
    private String maxPatients;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;


    @ManyToOne
    @JoinColumn(name="doctor_id", referencedColumnName = "id")
    private Doctor doctor;



}

