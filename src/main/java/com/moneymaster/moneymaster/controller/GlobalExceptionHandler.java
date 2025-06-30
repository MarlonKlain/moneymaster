package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    //ResponseEntity is a Spring Class that manages all the content that is going to be sent to the client
    //RuntimeException is the Exception shot by the application
    public ResponseEntity<ErrorResponseDto> handleExeceptions(RuntimeException ex, WebRequest request){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
