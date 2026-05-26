package com.btvn.ss10ex4.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private Integer discountPercent;
    private Boolean isActive;
}