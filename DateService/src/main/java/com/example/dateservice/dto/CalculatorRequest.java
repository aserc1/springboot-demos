package com.example.dateservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CalculatorRequest {
    @NotNull
    private String expression;
}
