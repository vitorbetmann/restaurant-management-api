package com.vitorbetmann.resmapi.exceptions;

public class NoFieldsToUpdateException extends RuntimeException {

    public NoFieldsToUpdateException(String message) {
        super(message);
    }
}