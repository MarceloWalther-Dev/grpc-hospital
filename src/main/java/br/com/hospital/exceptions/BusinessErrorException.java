package br.com.hospital.exceptions;

import io.grpc.Status;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public abstract class BusinessErrorException extends RuntimeException {

    public abstract Status getCode();

    @Override
    public abstract String getMessage();

    public Set<String> getDetails() {
        return Collections.emptySet();
    }

    @Getter
    private final LocalDateTime timestamp = LocalDateTime.now();
}
