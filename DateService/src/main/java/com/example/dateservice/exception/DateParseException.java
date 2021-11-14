package com.example.dateservice.exception;

import lombok.Getter;

public class DateParseException extends RuntimeException {
    @Getter
    private String date;

    public DateParseException(String date) {
        this.date = date;
    }
}
