package com.esoluzion.service.product.manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PriceOutputDTO {

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("brand_id")
    private Integer brandId;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

}