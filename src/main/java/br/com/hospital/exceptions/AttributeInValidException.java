package br.com.hospital.exceptions;

import io.grpc.Status;

public class AttributeInValidException extends BusinessErrorException {

    private String message;

    public AttributeInValidException(String message) {
        this.message = message;
    }

    @Override
    public Status getCode() {
        return Status.INVALID_ARGUMENT;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
