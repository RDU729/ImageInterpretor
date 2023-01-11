package com.api.imageinterpretor.exception;

public enum ErrorCodes implements GlobalErrorCodes {

    ///////////ERROR CODES///////////

    INTERNAL_SERVER_ERROR("Internal Server Error"),
    IMAGE_NOT_FOUND("Image not found");


    ////////////////////////////////

    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
