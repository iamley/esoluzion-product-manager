package com.esoluzion.service.product.manager.adapter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="PRICES")
public class PricesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="price_list")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceList;

    @Column(name="brand_id")
    private Integer brandId;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    @Column(name="product_id")
    private Integer productId;

    @Column(name="priority")
    private Integer priority;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="currency")
    private String money;
}
