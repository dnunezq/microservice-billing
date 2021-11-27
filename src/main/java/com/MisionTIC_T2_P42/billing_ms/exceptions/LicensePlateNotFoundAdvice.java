package com.MisionTIC_T2_P42.billing_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody

public class LicensePlateNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(LicensePlateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String EntityNotFoundAdvice(LicensePlateNotFoundException ex){
        return ex.getMessage();
    }
}








