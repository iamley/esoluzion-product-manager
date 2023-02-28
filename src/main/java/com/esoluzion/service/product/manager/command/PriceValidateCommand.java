package com.esoluzion.service.product.manager.command;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import com.esoluzion.service.product.manager.adapter.impl.GetProductList;
import com.esoluzion.service.product.manager.dto.EsoluzionResponseEntity;
import com.esoluzion.service.product.manager.dto.LoggerDataDTO;
import com.esoluzion.service.product.manager.dto.PriceOutputDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateRequestDTO;
import com.esoluzion.service.product.manager.dto.PriceValidateResponseDTO;
import com.esoluzion.service.product.manager.dto.StatusDataDTO;
import com.esoluzion.service.product.manager.exception.BusinessCapabilityException;
import com.esoluzion.service.product.manager.logic.PriceValidateLogic;
import com.esoluzion.service.product.manager.mapper.PricesMapper;
import com.esoluzion.service.product.manager.utils.EsoluzionCommand;
import com.esoluzion.service.product.manager.utils.EsoluzionStatusResponseUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.esoluzion.service.product.manager.enums.Status.NOT_FOUND;
import static com.esoluzion.service.product.manager.enums.Status.SUCCESS;
import static com.esoluzion.service.product.manager.enums.Status.FATAL_ERROR;
import static com.esoluzion.service.product.manager.enums.Status.FALLBACK;

@Slf4j
@Service
public class PriceValidateCommand implements EsoluzionCommand<PriceValidateRequestDTO, PriceValidateResponseDTO> {

    private static final String COMMAND_NAME = "PriceValidateCommand";

    private static Logger LOGGER = LoggerFactory.getLogger(PriceValidateCommand.class);

    private static final String EXCEPTION = "Request: {}\nException: {}";

    @Autowired
    private PriceValidateLogic priceValidateLogic;

    @Autowired
    private GetProductList repository;

    @Autowired
    private PricesMapper mapper;

    @Autowired
    private EsoluzionStatusResponseUtil statusResponseUtil;

    @Override
    @CircuitBreaker(name = COMMAND_NAME, fallbackMethod = "executeFallback")
    public EsoluzionResponseEntity<PriceValidateResponseDTO> execute(final PriceValidateRequestDTO request) throws BusinessCapabilityException {

        LOGGER.debug("Inicia la ejecucion del comando para obtener la tarifa final del producto");

        final var response = new EsoluzionResponseEntity<PriceValidateResponseDTO>();
        final var data = new PriceValidateResponseDTO();
        var output = new PriceOutputDTO();
        var status = new StatusDataDTO();

        try {
            priceValidateLogic.validateRequest(request);
            LOGGER.info("Resquest send to service {}", request);

            final List<PricesDTO> reply = repository
                    .findProductsByBrand(request.getBrandId(), request.getProductId());

            if (reply.stream().count() > 0) {
                final var price = mapper.toPricesValidateList(reply);
                output = priceValidateLogic.validateTimeframe(price, request);
                LOGGER.info("Response send to service {}", output);
            } else {
                throw new BusinessCapabilityException(
                        NOT_FOUND.getCode(),
                        NOT_FOUND.getDescription());
            }

            data.setBody(output);
            response.setBody(data);

            status.setCode(SUCCESS.getCode());
            status.setDescription(SUCCESS.getDescription());

        } catch (BusinessCapabilityException e) {
            LOGGER.error("BusinessCapabilityException", e);
            status.setCode(e.getReturnCode());
            status.setDescription(e.getReturnCodeDesc());
        }  catch (Exception e){
            LOGGER.error(EXCEPTION, request, e.toString());
            status.setCode(FATAL_ERROR.getCode());
            status.setDescription(FATAL_ERROR.getDescription());
        }
        data.setStatus(status);
        response.setBody(data);
        response.setStatus(status);
        return response;
    }

    @Override
    public EsoluzionResponseEntity<PriceValidateResponseDTO> executeFallback(final PriceValidateRequestDTO request, Throwable throwable) throws BusinessCapabilityException {

        final var dataLog = new LoggerDataDTO();
        dataLog.setMessage(throwable.toString());
        dataLog.setCode(FALLBACK.getCode());
        dataLog.setCodeMessage(FALLBACK.getDescription());
        dataLog.setLevel(LogLevel.ERROR);
        LOGGER.error("Fallo el comando para obtener la tarifa final del producto", dataLog, throwable);

        final var response = new EsoluzionResponseEntity<PriceValidateResponseDTO>();
        response.setStatus(statusResponseUtil.getStatusResponse(FALLBACK, throwable));

        return response;
    }

}