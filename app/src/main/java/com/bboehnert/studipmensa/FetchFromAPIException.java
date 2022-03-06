package com.bboehnert.studipmensa;

public class FetchFromAPIException extends Exception {
    private final String message;

    public FetchFromAPIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
