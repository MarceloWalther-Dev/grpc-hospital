package br.com.hospital.exceptions.handler;

import br.com.hospital.exceptions.BusinessErrorException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler(BusinessErrorException.class)
    public StatusRuntimeException handleBusinessErrorException(BusinessErrorException ex){
        return ex.getCode().withCause(ex.getCause()).withDescription(ex.getMessage()).asRuntimeException();
    }

}
