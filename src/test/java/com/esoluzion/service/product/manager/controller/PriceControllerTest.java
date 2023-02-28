package com.esoluzion.service.product.manager.controller;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.adapter.impl.GetProductList;
import com.esoluzion.service.product.manager.command.PriceValidateCommand;
import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateResponseDTO;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import com.esoluzion.service.product.manager.logic.PriceValidateLogic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.esoluzion.service.product.manager.enums.Status.BAD_REQUEST;
import static com.esoluzion.service.product.manager.enums.Status.FATAL_ERROR;
import static com.esoluzion.service.product.manager.enums.Status.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PriceController controller;

    @Mock
    private PriceValidateCommand command;

    @MockBean
    private PriceValidateLogic logic;

    @MockBean
    private GetProductList repository;

    private static PriceValidateRequestDTO request = new PriceValidateRequestDTO();

    private static PricesDTO prices = new PricesDTO();

    private static PriceOutputDTO response = new PriceOutputDTO();

    @BeforeEach
    public void setUp() throws Exception {
        request = new PriceValidateRequestDTO();
        request.setProductId(1);
        request.setBrandId(1);
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
    void getPricesListSuccess() throws Exception {

        List<PricesDTO> values = new ArrayList<>();
        values.add(prices);

        Mockito.when(repository.findProductsByBrand(any(), any())).thenReturn(values);
        Mockito.when(logic.validateTimeframe(any(), any())).thenReturn(response);

        MvcResult mvcResult = mockMvc
                .perform(post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();

        var reply = objectMapper.readValue(resultContent, PriceValidateResponseDTO.class);
        assertEquals(SUCCESS.getCode(), reply.getStatus().getCode());

    }

    @Test
    void getPricesListExpectactionFailed() throws Exception {

        Mockito.when(repository.findProductsByBrand(any(), any())).thenThrow(RuntimeException.class);
        Mockito.when(logic.validateTimeframe(any(), any())).thenThrow(RuntimeException.class);

        MvcResult mvcResult = mockMvc
                .perform(post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isExpectationFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();

        var reply = objectMapper.readValue(resultContent, PriceValidateResponseDTO.class);
        assertEquals(FATAL_ERROR.getCode(), reply.getStatus().getCode());
    }

}