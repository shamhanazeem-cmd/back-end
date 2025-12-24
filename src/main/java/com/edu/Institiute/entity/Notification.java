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
@Table(name = "Notification")

public class Notification {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="sentDate")
    private Date sentDate;

    @Column(name="channel")
    private String channel;

    @Column(name="createdBy ")
    private String createdBy;

    @Column(name="createdDate")
    private Date createdDate;

    @Column(name="modifyBy")
    private String modifyBy;

    @Column(name="modifyDate")
    private Date modifyDate;


    @ManyToOne
    @JoinColumn(name="appointment_Id", referencedColumnName = "id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private Status status;


}
