package com.edu.Institiute.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "rfq_header")
public class RFQHeader {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="rfqNumber")
    private String rfqNumber;

    @Column(name="requestDate")
    private Date requestDate;

    @Column(name="requestedBy")
    private String requestedBy;

    @Column(name="requiredDate")
    private Date requiredDate;

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

    @OneToMany(mappedBy = "rfqHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RFQDetails> details = new ArrayList<>() ;
}
