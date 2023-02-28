package com.esoluzion.service.product.manager.configurations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

    @InjectMocks
    private  SwaggerConfig swaggerConfig;

    @Test
    void apiTest(){
        ReflectionTestUtils.setField(swaggerConfig, "apiBaseUrl", "/v1");
        ReflectionTestUtils.setField(swaggerConfig, "swaggerEnabled", true);
        assertNotNull(swaggerConfig.api());
    }

}
