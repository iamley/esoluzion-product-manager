package com.esoluzion.service.product.manager.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class BusinessCapabilityException extends RuntimeException {

    private static final long serialVersionUID = -6821998361199415787L;

    private final String returnCode;
    private final String returnCodeDesc;

    public BusinessCapabilityException(String returnCode, String returnCodeDesc) {
        super(returnCodeDesc);
        this.returnCode = returnCode;
        this.returnCodeDesc = returnCodeDesc;
    }

}