package com.esoluzion.service.product.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.logging.LogLevel;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggerDataDTO {

    @NonNull
    private LogLevel level;
    private String message;
    private String path;
    private String code;
    private String codeMessage;
    private Long executeTime;
    private String headers;
    private String requestId;

}
