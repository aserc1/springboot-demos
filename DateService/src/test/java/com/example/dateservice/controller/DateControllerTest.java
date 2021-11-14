package com.example.dateservice.controller;

import com.example.dateservice.dto.DateResponse;
import com.example.dateservice.dto.Result;
import com.example.dateservice.exception.DateParseException;
import com.example.dateservice.service.DateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DateController.class)
class DateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DateService dateService;

    @Test
    public void code_10000_when_date_valid() throws Exception {
        DateResponse response = new DateResponse();
        response.setDayOfWeek("五");
        Result<DateResponse> result = Result.success(response);
        Mockito.when(dateService.getDayOfWeek(Mockito.any())).thenReturn(result);

        mockMvc.perform(post("/rest/v1/date/day_of_week")
                        .contentType("application/json")
                        .content("{\"date\": \"2021-10-5\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retCode").value(1000));
    }

    @Test
    public void code_1002_when_date_inValid() throws Exception {
        Mockito.when(dateService.getDayOfWeek(Mockito.any())).thenThrow(new DateParseException("2021-10-222"));

        mockMvc.perform(post("/rest/v1/date/day_of_week")
                        .contentType("application/json")
                        .content("{\"date\": \"2021-10-222\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.retCode").value(1002));
    }

    @Test
    public void code_1002_when_date_parse_fail() throws Exception {
        Mockito.when(dateService.getDayOfWeek(Mockito.any())).thenThrow(new DateParseException("2021-10-50"));

        mockMvc.perform(post("/rest/v1/date/day_of_week")
                        .contentType("application/json")
                        .content("{\"date\": \"2021-10-50\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.retCode").value(1002));
    }
}