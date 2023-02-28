package com.esoluzion.service.product.manager.logic.impl;

import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import com.esoluzion.service.product.manager.logic.PriceValidateLogic;
import com.esoluzion.service.product.manager.mapper.PricesMapper;
import com.esoluzion.service.product.manager.model.PricesModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.esoluzion.service.product.manager.enums.Status.BAD_REQUEST;
import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;


@Slf4j
@Service
public class PriceValidateLogicImpl implements PriceValidateLogic {

    private static Logger LOGGER = LoggerFactory.getLogger(PriceValidateLogicImpl.class);

    @Autowired
    private PricesMapper mapper;

    @Override
    public void validateRequest(PriceValidateRequestDTO request) throws BusinessCapabilityException {

        List<Object> requestList = new ArrayList<>();
        requestList.add(request.getDate());
        requestList.add(request.getBrandId());
        requestList.add(request.getProductId());

        for(Object value : requestList) {
            if(value == null) {
                throw new BusinessCapabilityException(
                        BAD_REQUEST.getCode(),
                        BAD_REQUEST.getDescription());
            }

            if(value instanceof String && ((String) value).isBlank()) {
                throw new BusinessCapabilityException(
                        BAD_REQUEST.getCode(),
                        BAD_REQUEST.getDescription());
            }
        }
    }

    @Override
    public PriceOutputDTO validateTimeframe(List<PricesModelDTO> values, PriceValidateRequestDTO request) {

        LocalDateTime dateNow = request.getDate();
        List<PricesModelDTO> outputList = new ArrayList<>();

        for (PricesModelDTO value : values) {
            if (dateNow.isAfter(value.getStartDate()) && dateNow.isBefore(value.getEndDate())) {
                outputList.add(value);
            }
        }

        if(outputList != null) {
            PricesModelDTO value = outputList
                    .stream()
                    .max(Comparator.comparing(PricesModelDTO::getPriority))
                    .orElseThrow(NoSuchElementException::new);

            return mapper.toPricesOutputDto(value);
        } else {
            throw new BusinessCapabilityException(
                    NOT_FOUND.getCode(),
                    NOT_FOUND.getDescription());
        }

    }

}