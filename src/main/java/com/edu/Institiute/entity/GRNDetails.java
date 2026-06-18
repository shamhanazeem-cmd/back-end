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
@Table(name = "grn_details")
public class GRNDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="grnItem")
    private String grnItem;

    @Column(name="orderedQty")
    private Integer orderedQty;

    @Column(name="receivedQty")
    private Integer receivedQty;

    @Column(name="damagedQty")
    private Integer damagedQty;

    @Column(name="acceptedQty")
    private Integer acceptedQty;

    @ManyToOne
    @JoinColumn(name = "grn_header_id")
    private GRNHeader grnHeader;
}
