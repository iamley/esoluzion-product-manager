package com.esoluzion.service.product.manager.adapter.impl;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.adapter.repository.PriceRepository;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.esoluzion.service.product.manager.enums.Status.DATABASE_ERROR;

@Repository
public class GetProductList {

    private static Logger LOGGER = LoggerFactory.getLogger(GetProductList.class);

    @Lazy
    @Autowired
    private PriceRepository repository;

    public List<PricesDTO> findProductsByBrand(Integer brand, Integer product) {
        try {
            return repository.findPricesList(brand, product);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessCapabilityException(DATABASE_ERROR.getCode(), DATABASE_ERROR.getDescription());
        }
    }
}
