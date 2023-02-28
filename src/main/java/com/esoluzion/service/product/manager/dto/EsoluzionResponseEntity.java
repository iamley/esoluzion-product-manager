package com.esoluzion.service.product.manager.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"status", "body"})
public class EsoluzionResponseEntity<T> {

    protected transient T body;
    protected StatusDataDTO status;
}
