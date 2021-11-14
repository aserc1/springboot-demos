package com.example.dateservice.controller;

import com.example.dateservice.dto.CalculatorRequest;
import com.example.dateservice.dto.CalculatorResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.service.CalculateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/v1/calculate")
public class CalculatorController {
    private final CalculateService calculateService;

    public CalculatorController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @PostMapping
    public Result<CalculatorResponse> calculateExpression(@Valid @RequestBody CalculatorRequest request){
        return calculateService.calculate(request.getExpression());
    }
}
