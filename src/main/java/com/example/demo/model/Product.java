package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = -329404315134592305L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private BigDecimal cartonPrice;

    @Column(nullable = false)
    private Integer unitsPerCarton;

    @Column(nullable = false)
    private BigDecimal priceMultiplier;

    public Product() {
    }

    public Product(Long id, String name, String code, BigDecimal cartonPrice, Integer unitsPerCarton, BigDecimal priceMultiplier) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cartonPrice = cartonPrice;
        this.unitsPerCarton = unitsPerCarton;
        this.priceMultiplier = priceMultiplier;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getCartonPrice() {
        return cartonPrice;
    }

    public Integer getUnitsPerCarton() {
        return unitsPerCarton;
    }

    public BigDecimal getPriceMultiplier() {
        return priceMultiplier;
    }

}
