package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ClientesNotFoundAdvice {
@ResponseBody
@ExceptionHandler(ClientesNotFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
String EntityNotFoundAdvice(ClientesNotFoundException ex){
return ex.getMessage();
}
}