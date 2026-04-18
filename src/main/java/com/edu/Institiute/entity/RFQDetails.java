package com.edu.Institiute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "rfq_details")
public class RFQDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item")
    private String item;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "rfqHeader")
    @JsonIgnore
    private RFQHeader rfqHeader;
}
