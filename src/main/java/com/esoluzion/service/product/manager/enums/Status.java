package com.esoluzion.service.product.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {

    SUCCESS("0000", "Service executed successfully.", HttpStatus.OK),
    BAD_REQUEST("0001", "Bad request, missing input data in the request.", HttpStatus.BAD_REQUEST),
    FATAL_ERROR("0002", "Unexpected error in service execution.", HttpStatus.EXPECTATION_FAILED),
    INTERNAL_ERROR("0003", "Unknown Error.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("0004", "Error, not found data in the database.", HttpStatus.NOT_FOUND),
    FALLBACK("0005", "Please try again, we are experiencing some issues.", HttpStatus.SERVICE_UNAVAILABLE),
    CIRCUIT_BREAKER("0006", "Error, services are currently unavailable, please try again.", HttpStatus.SERVICE_UNAVAILABLE),
    REQUEST_FAILED("0007", "Error in the request or some field may be empty.", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR("0008", "Failed to connect to database.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
}
