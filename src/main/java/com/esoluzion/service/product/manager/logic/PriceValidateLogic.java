package com.esoluzion.service.product.manager.logic;

import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.model.PricesModelDTO;

import java.util.List;

public interface PriceValidateLogic {

    void validateRequest(PriceValidateRequestDTO request);
    PriceOutputDTO validateTimeframe(List<PricesModelDTO> values, PriceValidateRequestDTO request);

}