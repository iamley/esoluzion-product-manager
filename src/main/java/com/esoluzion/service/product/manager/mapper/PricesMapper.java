package com.esoluzion.service.product.manager.mapper;

import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.model.PricesModelDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricesMapper {

    @InheritInverseConfiguration
    PriceOutputDTO toPricesOutputDto(PricesModelDTO value);

}