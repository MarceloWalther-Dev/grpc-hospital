package br.com.hospital.exceptions;

import io.grpc.Status;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class DoctorNotFoundException extends BusinessErrorException {

    private String message;

    public DoctorNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public Status getCode() {
        return Status.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
