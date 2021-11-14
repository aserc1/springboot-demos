package com.example.dateservice.exception;

import lombok.Getter;

public class ExpressionFormatException extends RuntimeException{
    @Getter
    private String expression;

    public ExpressionFormatException(String expression){
        this.expression=expression;
    }
}
