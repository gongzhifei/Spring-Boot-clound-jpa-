package com.xxd.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gongzhifei
 */
@ControllerAdvice
public class ControllerException extends Throwable {

    @ExceptionHandler(DataValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handleDataValidationException(DataValidationException ex){
        Map<String,Object> result = new HashMap<>();
        result.put("className",ex.getClassName());
        result.put("message",ex.getMessage());
        return result;
    }

    @ExceptionHandler(ReturnMessageException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map<String,Object> handleReturnMessageException(ReturnMessageException ex){
        Map<String,Object> result = new HashMap<>();
        result.put("success",ex.isSuccess());
        result.put("message",ex.getMessage());
        return result;
    }

}
