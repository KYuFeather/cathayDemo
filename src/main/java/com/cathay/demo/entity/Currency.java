package com.cathay.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "currency")
@Table
@Data
public class Currency {
    @Id
    @Column(unique = true, nullable = false)
    private String currEng;

    @Column(unique = true, nullable = false)
    private String currChn;
}
