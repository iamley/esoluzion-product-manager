package com.esoluzion.service.product.manager.adapter.impl;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.adapter.repository.PriceRepository;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GetProductListTest {

    @MockBean
    private PriceRepository repository;

    @Autowired
    private GetProductList getProductList;

    private static PricesDTO prices = new PricesDTO();

    @BeforeAll
    public static void setUp() {
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
        prices = null;
    }

    @Test
    void getProductsByBrandSuccess() {

        List<PricesDTO> values = new ArrayList<>();
        values.add(prices);

        Mockito.when(repository.findPricesList(any(), any())).thenReturn(values);

        List<PricesDTO> response = getProductList.findProductsByBrand(1, 1);
        assertNotNull(response);
    }

    @Test()
    void getProductsByBrandExceptionTest() {

        Mockito.when(repository.findPricesList(any(), any())).thenThrow(Mockito.mock(DataAccessException.class));

        assertThrows(BusinessCapabilityException.class, () -> getProductList.findProductsByBrand(0, 0));

    }
}
