package com.example.dateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private int retCode;
    private String message;
    private T data;

    public static Result<Object> fail(String message){
        return new Result<>(1002,message,null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(1000,null,data);
    }
}
