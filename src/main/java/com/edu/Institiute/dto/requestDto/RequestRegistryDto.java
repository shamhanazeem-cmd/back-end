 package com.edu.Institiute.dto.requestDto;

import com.edu.Institiute.dto.*;
import com.edu.Institiute.entity.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class RequestRegistryDto {


    private String studentCode;
    private String studentName;
    private String studentAge;
    private String studentNic;
    private Integer status;

    private String courseCode;
    private String courseName;

    private String allergies;
    private String pastSurgeries;
    private String chronicConditions;
    private String medicalHistory;

    private String patientSerialID;
    private String fullName;
    private String nic;
    private String dob;
    private String gender;
    private String address;
    private String contactNo;
    private String email;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private Integer patientMedicalHistory;

    private String name;
    private String description;

    private String doctorSerialID;
    private String doctorName;
    private String contactDetails;
    private String mail;
    private String roomNo;
    private String specializations;


    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String slotDuration;
    private String maxPatients;
    private Integer doctor;

    private String appointmentSerialID;
    private String appointmentDate;
    private String appointmentTime;
    private Integer patient;
    private Integer schedule;

    private Date prescriptionDate;
    private String notes;
    private Integer Appointment;


    private Integer id;
    private String drugName;
    private String dosage;
    private String duration;
    private String instructions;
    private Integer prescription;


    private Date sentDate;
    private String channel;


    private String paymentSerialID;
    private Integer hospitalCharge;
    private Integer doctorCharge;
    private Integer tax;
    private Integer amount;
    private String paymentMethod;
    private Date paymentDate;


    private String invoiceNumber;
    private Date issuedDate;
    private Integer totalAmount;
    private Integer payment;



}
