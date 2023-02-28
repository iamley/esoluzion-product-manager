package com.esoluzion.service.product.manager.utils.impl;


import com.esoluzion.service.product.manager.dto.StatusDataDTO;
import com.esoluzion.service.product.manager.enums.Status;
import com.esoluzion.service.product.manager.utils.EsoluzionStatusResponseUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.esoluzion.service.product.manager.enums.Status.REQUEST_FAILED;

@Component("EsoluzionStatusResponseUtil")
public class EsoluzionStatusResponseUtilImpl implements EsoluzionStatusResponseUtil {

    public StatusDataDTO getStatusResponse(final Status status) {
        return StatusDataDTO.builder().code(status.getCode()).description(status.getDescription())
                .httpStatus(status.getHttpStatus()).build();
    }

    public StatusDataDTO getStatusResponse(final Status status, final Throwable throwable) {

        if (throwable instanceof MethodArgumentTypeMismatchException) {
            return getStatusResponse(REQUEST_FAILED);
        }

        if (throwable instanceof MethodArgumentNotValidException) {
            return getStatusResponse(REQUEST_FAILED);
        }

        if (throwable instanceof HttpMessageNotReadableException) {
            return getStatusResponse(REQUEST_FAILED);
        }

        if (throwable instanceof ServletRequestBindingException) {
            return getStatusResponse(REQUEST_FAILED);
        }

        if (throwable instanceof MissingServletRequestParameterException) {
            return getStatusResponse(REQUEST_FAILED);
        }

        return getStatusResponse(status);
    }

}