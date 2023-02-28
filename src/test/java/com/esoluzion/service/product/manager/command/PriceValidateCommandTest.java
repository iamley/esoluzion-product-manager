package com.esoluzion.service.product.manager.command;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.adapter.impl.GetProductList;
import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.logic.PriceValidateLogic;
import com.esoluzion.service.product.manager.mapper.PricesMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.esoluzion.service.product.manager.enums.Status.FATAL_ERROR;
import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;
import static com.esoluzion.service.product.manager.enums.Status.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PriceValidateCommandTest {

    @SpyBean
    private PriceValidateCommand command;

    @MockBean
    private PriceValidateLogic logic;

    @MockBean
    private GetProductList repository;

    @Autowired
    private PricesMapper mapper;

    @Captor
    private ArgumentCaptor<Throwable> exceptionCaptor;

    private static PriceValidateRequestDTO request = new PriceValidateRequestDTO();

    private static PricesDTO prices = new PricesDTO();

    private static PriceOutputDTO response = new PriceOutputDTO();

    @BeforeAll
    public static void setUp() {
        request.setBrandId(1);
        request.setProductId(35455);
        request.setDate(LocalDateTime.now());

        response.setPrice(BigDecimal.valueOf(15.20));
        response.setProductId(25648);
        response.setBrandId(1);
        response.setStartDate(LocalDateTime.now());
        response.setEndDate(LocalDateTime.now());

        prices.setPrice(BigDecimal.valueOf(15.20));
        prices.setProductId(25648);
        prices.setBrandId(1);
        prices.setPriceList(1);
        prices.setStartDate(LocalDateTime.now());
        prices.setEndDate(LocalDateTime.now());
        prices.setMoney("EUR");
        prices.setPriority(1);
    }

    @AfterAll
    public static void tearDown() {
        request = null;
        response = null;
        prices = null;
    }

    @Test
    void executeCompleteSuccess() {

        List<PricesDTO> values = new ArrayList<>();
        values.add(prices);

        Mockito.when(repository.findProductsByBrand(any(), any())).thenReturn(values);
        Mockito.when(logic.validateTimeframe(any(), any())).thenReturn(response);

        final var response = command.execute(request);

        assertNotNull(response);
        assertEquals(SUCCESS.getCode(), response.getStatus().getCode());
    }

    @Test
    void executeCompleteWithException() {

        Mockito.when(repository.findProductsByBrand(any(), any())).thenThrow(RuntimeException.class);
        Mockito.when(logic.validateTimeframe(any(), any())).thenThrow(RuntimeException.class);

        final var response = command.execute(request);

        assertNotNull(response);
        assertEquals(FATAL_ERROR.getCode(), response.getStatus().getCode());
    }

    @Test
    void executeCompleteWithExceptionNotFound() {

        final var response = command.execute(request);

        assertNotNull(response);
        assertEquals(NOT_FOUND.getCode(), response.getStatus().getCode());
    }

}