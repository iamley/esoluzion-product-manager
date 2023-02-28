package com.esoluzion.service.product.manager.utils;

import com.esoluzion.service.product.manager.dto.StatusDataDTO;
import com.esoluzion.service.product.manager.enums.Status;

public interface EsoluzionStatusResponseUtil {

    StatusDataDTO getStatusResponse(final Status status);

    StatusDataDTO getStatusResponse(final Status status, final Throwable throwable);

}