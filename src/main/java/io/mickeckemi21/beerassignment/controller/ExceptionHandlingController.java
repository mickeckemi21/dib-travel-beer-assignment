package io.mickeckemi21.beerassignment.controller;

import io.mickeckemi21.beerassignment.exception.ClientException;
import io.mickeckemi21.beerassignment.exception.EntityNotFountException;
import io.mickeckemi21.beerassignment.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFountException.class)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFountException exception) {
        return new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ClientException.class)
    public ExceptionResponse handleClientException(ClientException exception) {
        return new ExceptionResponse(LocalDateTime.now(), exception.getMessage());
    }

}
