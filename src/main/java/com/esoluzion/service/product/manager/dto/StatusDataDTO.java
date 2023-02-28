package com.esoluzion.service.product.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDataDTO {

    protected String code;
    protected String description;

    @JsonIgnore
    private HttpStatus httpStatus;

}