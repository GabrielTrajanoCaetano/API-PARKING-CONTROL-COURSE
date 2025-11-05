package com.mballem.demo_park_api.exception;

import lombok.Getter;

@Getter
public class VagaDisponivelException extends RuntimeException{

    private String recurso;
    private String disponivel;

    public VagaDisponivelException(String recurso, String disponivel){
        this.recurso = recurso;
        this.disponivel = disponivel;
    }

    public VagaDisponivelException(String message) {
        super(message);
    }
}
