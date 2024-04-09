package com.runtik.greenatom_test.controller;

import com.runtik.greenatom_test.exception.ForbiddenException;
import com.runtik.greenatom_test.exception.NotFoundException;
import com.runtik.greenatom_test.exception.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.runtik.greenatom_test.util.MessageContent.*;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void notFoundException(NotFoundException ex, WebRequest request) {
        log.debug(NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public void validationException(ValidationException ex, WebRequest request) {
        log.debug(VALIDATION_EXCEPTION_MESSAGE);
    }
    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void forbiddenException(ValidationException ex, WebRequest request) {
        log.debug(ACCESS_FORBIDDEN_MESSAGE);
    }
}
