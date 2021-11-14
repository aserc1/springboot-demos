package com.example.dateservice.exception;

import com.example.dateservice.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

/**
 * 全局Controller层异常处理
 *
 * @author jgblm
 * @date 2021/11/12
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    private static final String DATE_PARSE_ERROR_MESSAGE = "{0} is not a valid date";

    private static final String INVALID_EXPRESSION_MESSAGE = "Invalid expression: {0}";

    /**
     * 参数校验失败异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link Object}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        return Result.fail(msg);
    }

    /**
     * 日期转换失败异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link Object}>
     */
    @ExceptionHandler(DateParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> handleMethodArgumentNotValidException(DateParseException ex) {
        return Result.fail(MessageFormat.format(DATE_PARSE_ERROR_MESSAGE,ex.getDate()));
    }

    /**
     * 表达式格式异常处理
     *
     * @param ex 异常
     * @return {@link Result}<{@link Object}>
     */
    @ExceptionHandler(ExpressionFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result<Object> handleMethodArgumentNotValidException(ExpressionFormatException ex) {
        return Result.fail(MessageFormat.format(INVALID_EXPRESSION_MESSAGE,ex.getExpression()));
    }
}
