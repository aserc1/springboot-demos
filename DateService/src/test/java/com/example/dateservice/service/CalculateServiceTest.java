package com.example.dateservice.service;

import com.example.dateservice.dto.CalculatorResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.ExpressionFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateServiceTest {

    @Test
    public void success_when_expression_valid() {
        CalculateService service = new CalculateService();
        Result<CalculatorResponse> result = service.calculate("2+3*(-5)-1/2");
        assertEquals(1000, result.getRetCode());
        assertEquals(-13.5, result.getData().getResult());
    }

    @Test
    public void throw_exception_when_expression_invalid() {
        CalculateService service = new CalculateService();
        assertThrows(ExpressionFormatException.class, () -> service.calculate("2+3*(--5)"));
    }
}