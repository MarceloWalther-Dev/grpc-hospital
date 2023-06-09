package br.com.hospital.controller;

import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.service.DoctorService;
import br.com.hospital.utils.Validation;
import hospital.DoctorInput;
import hospital.DoctorOutput;
import hospital.DoctorOutputList;
import hospital.DoctorServiceGrpc;
import hospital.EmptyInput;
import hospital.EmptyOutput;
import hospital.InputId;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class DoctorController extends DoctorServiceGrpc.DoctorServiceImplBase {

    private final DoctorService service;


    @Override
    public void create(DoctorInput request, StreamObserver<DoctorOutput> responseObserver) {
        var doctorRequest = new DoctorRequest(request);
        Validation.validateRequiredAttributes(doctorRequest);
        var response = service.create(doctorRequest);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(InputId request, StreamObserver<DoctorOutput> responseObserver) {
        var response = service.findById(request.getId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(InputId request, StreamObserver<EmptyOutput> responseObserver) {
        service.delete(request.getId());
        responseObserver.onNext(EmptyOutput.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(EmptyInput request, StreamObserver<DoctorOutputList> responseObserver) {
        var doctorList = service.findAll();
        var doctorOutputList = DoctorOutputList.newBuilder().addAllDoctor(doctorList).build();
        responseObserver.onNext(doctorOutputList);
        responseObserver.onCompleted();
    }


}
