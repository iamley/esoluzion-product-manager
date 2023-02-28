package com.esoluzion.service.product.manager.controller;

import com.esoluzion.service.product.manager.command.PriceValidateCommand;
import com.esoluzion.service.product.manager.dto.EsoluzionResponseEntity;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateResponseDTO;
import com.esoluzion.service.product.manager.dto.StatusDataDTO;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.esoluzion.service.product.manager.enums.Status.BAD_REQUEST;
import static com.esoluzion.service.product.manager.enums.Status.CIRCUIT_BREAKER;
import static com.esoluzion.service.product.manager.enums.Status.FATAL_ERROR;
import static com.esoluzion.service.product.manager.enums.Status.INTERNAL_ERROR;
import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;
import static com.esoluzion.service.product.manager.enums.Status.REQUEST_FAILED;

@RestController
@Api(tags = "Controlador de operaciones de consulta de precios")
public class PriceController {

    private static Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceValidateCommand priceValidateCommand;

    @PostMapping(path = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta de precios por marca y producto", httpMethod = "POST", nickname = "Consulta Precios")
    public ResponseEntity<PriceValidateResponseDTO> priceValidate(@RequestBody PriceValidateRequestDTO request) {

        LOGGER.debug("Inicio de la consulta de precio");
        ResponseEntity<PriceValidateResponseDTO> reply;

        try {
            final EsoluzionResponseEntity<PriceValidateResponseDTO> response =
                    priceValidateCommand.execute(request);

            reply = new ResponseEntity<>(response.getBody(), null, getHttpStatus(response));
        } catch (BusinessCapabilityException be) {
            final var fatalError = new PriceValidateResponseDTO();
            final var errorStatus = new StatusDataDTO();
            errorStatus.setCode(be.getReturnCode());
            errorStatus.setDescription(be.getMessage());
            fatalError.setStatus(errorStatus);

            reply = new ResponseEntity<>(fatalError, null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            final var fatalError = new PriceValidateResponseDTO();
            final var errorStatus = new StatusDataDTO();
            errorStatus.setCode(FATAL_ERROR.getCode());
            errorStatus.setDescription(FATAL_ERROR.getDescription());
            fatalError.setStatus(errorStatus);

            reply = new ResponseEntity<>(fatalError, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.debug("Fin de la consulta de precios");
        return reply;
    }

    private HttpStatus getHttpStatus(EsoluzionResponseEntity<PriceValidateResponseDTO> response) {
        if (response.getStatus().getCode().equals(BAD_REQUEST.getCode())) {
            return HttpStatus.BAD_REQUEST;
        } else if (response.getStatus().getCode().equals(FATAL_ERROR.getCode())) {
            return HttpStatus.EXPECTATION_FAILED;
        } else if (response.getStatus().getCode().equals(INTERNAL_ERROR.getCode())) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (response.getStatus().getCode().equals(NOT_FOUND.getCode())) {
            return HttpStatus.NOT_FOUND;
        } else if (response.getStatus().getCode().equals(CIRCUIT_BREAKER.getCode())) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (response.getStatus().getCode().equals(REQUEST_FAILED.getCode())) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.OK;
        }
    }
}