package com.esoluzion.service.product.manager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceValidateResponseDTO {

    private PriceOutputDTO body;
    private StatusDataDTO status;

}