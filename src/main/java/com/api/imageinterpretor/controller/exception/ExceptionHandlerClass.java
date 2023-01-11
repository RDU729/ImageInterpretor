package com.api.imageinterpretor.controller.exception;

import com.api.imageinterpretor.exception.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleSqlIntegrityException(HttpServletRequest req, ServiceException ex) {

        String error =  ex.getMessage();
        ErrorCodes errorCode = ex.getErrorCode();
        return buildResponseEntity(new ErrorResponse(errorCode, HttpStatus.BAD_REQUEST, error));
    }

    private ResponseEntity<?> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
