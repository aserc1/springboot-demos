package com.example.dateservice.controller;

import com.example.dateservice.dto.DateRequest;
import com.example.dateservice.dto.DateResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.service.DateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/date")
@Slf4j
public class DateController {
    private final DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @PostMapping("/day_of_week")
    public Result<DateResponse> getDayOfWeek(@RequestBody @Validated DateRequest dateRequest){
        return dateService.getDayOfWeek(dateRequest);
    }
}
