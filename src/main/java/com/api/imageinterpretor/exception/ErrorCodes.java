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
    INVALID_EMAIL_ADDRESS_FORMAT("Email address does not appear to be valid"),

    IMAGE_NOT_FOUND("Image not found"),
    IMAGE_COULD_NOT_BE_SAVED("Error occurred when trying to save image"),

    COULD_NOT_READ_WRITE_FILE_FOR_PREDICTION("The temporary used to make predictions could not be read or wrote"),

    EXCEEDED_3_OFFENCES("Account will be deactivated for 1 minute"),
    HARMFUL_FILE("Harmful file detected! Only jpg allowed"),
    UNSUPPORTED_FILE_TYPE("Wrong file format uploaded");


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
