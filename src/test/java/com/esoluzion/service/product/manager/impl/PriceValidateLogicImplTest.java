package com.esoluzion.service.product.manager.impl;

import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import com.esoluzion.service.product.manager.logic.impl.PriceValidateLogicImpl;
import com.esoluzion.service.product.manager.mapper.PricesMapper;
import com.esoluzion.service.product.manager.model.PricesModelDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.esoluzion.service.product.manager.enums.Status.BAD_REQUEST;
import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PriceValidateLogicImplTest {

    @Mock
    private PriceValidateLogicImpl priceValidateLogic;

    @Mock
    private PricesMapper mapper;

    private static PriceValidateRequestDTO request = new PriceValidateRequestDTO();

    private static PricesModelDTO model = new PricesModelDTO();

    @BeforeAll
    public static void setUp() {

        request.setBrandId(1);
        request.setProductId(35455);
        request.setDate(LocalDateTime.now());

        model.setPrice(BigDecimal.valueOf(15.20));
        model.setProductId(35455);
        model.setBrandId(1);
        model.setStartDate(LocalDateTime.now());
        model.setEndDate(LocalDateTime.now());
    }

    @AfterAll
    public static void tearDown() {
        request = null;
        model = null;
    }

    @Test
    public void validateRequestSuccess() {

        request.setBrandId(null);
        request.setProductId(null);

        try {
            priceValidateLogic.validateRequest(request);
        } catch (BusinessCapabilityException e) {
            Assertions.assertEquals(BAD_REQUEST.getCode(), e.getReturnCode());
        }

    }

    @Test
    public void validateTimeframeExceptionNotFound() {

        model.setStartDate(LocalDateTime.now());
        model.setEndDate(LocalDateTime.now());

        List<PricesModelDTO> values = new ArrayList<>();
        values.add(model);

        try {
            priceValidateLogic.validateTimeframe(values, request);
        } catch (BusinessCapabilityException e) {
            Assertions.assertEquals(NOT_FOUND.getCode(), e.getReturnCode());
        }

    }
}