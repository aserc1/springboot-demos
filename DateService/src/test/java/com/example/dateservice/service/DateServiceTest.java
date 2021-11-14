package com.example.dateservice.service;

import com.example.dateservice.dto.DateRequest;
import com.example.dateservice.dto.DateResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.DateParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {
    @Test
    public void get_dayOfWeek_success_when_date_valid(){
        DateRequest request = new DateRequest();
        request.setDate("2021-11-5");
        DateService dateService = new DateService();
        Result<DateResponse> result = dateService.getDayOfWeek(request);
        assertEquals("五",result.getData().getDayOfWeek());
    }

    @Test
    public void throw_DateParseException_when_date_invalid(){
        DateRequest request = new DateRequest();
        request.setDate("2021-11-51");
        DateService dateService = new DateService();
        assertThrows(DateParseException.class,()->dateService.getDayOfWeek(request));
    }

}