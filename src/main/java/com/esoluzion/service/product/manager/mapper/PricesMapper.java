package com.esoluzion.service.product.manager.mapper;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.model.PricesModelDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PricesMapper {

    @InheritInverseConfiguration
    PriceOutputDTO toPricesOutputDto(PricesModelDTO value);

    @InheritInverseConfiguration
    @IterableMapping(elementTargetType = PricesModelDTO.class)
    List<PricesModelDTO> toPricesValidateList(List<PricesDTO> values);

}