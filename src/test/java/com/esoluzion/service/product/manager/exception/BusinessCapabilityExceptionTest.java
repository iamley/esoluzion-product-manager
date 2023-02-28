package com.esoluzion.service.product.manager.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class BusinessCapabilityExceptionTest {

    @Mock
    private BusinessCapabilityException businessCapabilityException;

    @Test
    void codeDescriptionWhenOfWrapper() {
        assertThatThrownBy(() -> {
            throw new BusinessCapabilityException(NOT_FOUND.getCode(), NOT_FOUND.getDescription());
        }).isInstanceOf(BusinessCapabilityException.class).hasStackTraceContaining("BusinessCapabilityException");
    }

}