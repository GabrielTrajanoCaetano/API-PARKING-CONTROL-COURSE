package com.mballem.demo_park_api.exception;

public class VagaDisponivelException extends RuntimeException{
    public VagaDisponivelException(String message) {
        super(message);
    }
}
