package com.api.imageinterpretor.exception;

public enum ErrorCodes implements GlobalErrorCodes {

    ///////////ERROR CODES///////////

    INTERNAL_SERVER_ERROR("Internal Server Error"),

    OPTIONAL_FOUND_EMPTY("Optional, contained no value"),

    ACTIVATION_TOKEN_DID_NOT_MATCH_ANY_USER("Activation token did not correspond to any user"),
    ACTIVATION_TOKEN_MATCHED_MORE_THAN_ONE_USER("Activation token activated more than one user"),

    UNABLE_TO_SEND_EMAIL("Unable to send email"),

    FILE_COULD_NOT_BE_READ("Error occurred when trying to read file"),

    USER_ALREADY_EXISTS_WITH_THIS_EMAIL("There is another account with the same email address"),

    IMAGE_NOT_FOUND("Image not found"),
    IMAGE_COULD_NOT_BE_SAVED("Error occurred when trying to save image");


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
