package com.esoluzion.service.product.manager.utils;

import com.esoluzion.service.product.manager.dto.EsoluzionResponseEntity;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;

public interface EsoluzionCommand<T, R> {

    EsoluzionResponseEntity<R> execute(T request) throws BusinessCapabilityException;

    EsoluzionResponseEntity<R> executeFallback(T request, Throwable throwable)
        throws BusinessCapabilityException;
}