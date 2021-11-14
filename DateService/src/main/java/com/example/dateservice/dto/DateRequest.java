package com.example.dateservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DateRequest {
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}$")
    private String date;
}
