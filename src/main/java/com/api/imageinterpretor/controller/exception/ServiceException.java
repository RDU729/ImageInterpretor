package com.api.imageinterpretor.controller.exception;

import com.api.imageinterpretor.exception.ErrorCodes;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private ErrorCodes errorCode;
    private String message;

    public ServiceException(ErrorCodes errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ServiceException(String message, ErrorCodes errorCode, String message1) {
        super(message);
        this.errorCode = errorCode;
        this.message = message1;
    }

    public ServiceException(String message, Throwable cause, ErrorCodes errorCode, String message1) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message1;
    }

    public ServiceException(Throwable cause, ErrorCodes errorCode, String message) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCodes errorCode, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.message = message1;
    }
}