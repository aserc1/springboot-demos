package com.example.dateservice.controller;

import com.example.dateservice.dto.CalculatorResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.ExpressionFormatException;
import com.example.dateservice.service.CalculateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateService service;

    @Test
    public void success_when_expression_valid() throws Exception {
        CalculatorResponse response = new CalculatorResponse();
        response.setResult(13.5);
        Mockito.when(service.calculate(Mockito.any())).thenReturn(Result.success(response));

        mockMvc.perform(post("/rest/v1/calculate")
                        .contentType("application/json")
                        .content("{\"expression\": \"1+2*5\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retCode").value(1000));

    }

    @Test
    public void fail_when_expression_invalid() throws Exception {
        Mockito.when(service.calculate(Mockito.any())).thenThrow(new ExpressionFormatException("1++2"));

        mockMvc.perform(post("/rest/v1/calculate")
                        .contentType("application/json")
                        .content("{\"expression\": \"1++2\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.retCode").value(1002));

    }
}