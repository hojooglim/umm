package com.example.umm.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionDto> NullHandleException(NullPointerException NullEx){
        ExceptionDto exceptionDto = new ExceptionDto(NullEx.getMessage(),400);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionDto> IllHandleException(IllegalArgumentException IllegalEx){
        ExceptionDto exceptionDto = new ExceptionDto(IllegalEx.getMessage(),400);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDto> ValHandleException(MethodArgumentNotValidException validationEx){
        ExceptionDto exceptionDto = new ExceptionDto(validationEx.getAllErrors().get(0).getDefaultMessage(),400);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }



}
