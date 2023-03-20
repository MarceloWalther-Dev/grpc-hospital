package br.com.hospital.controller;

import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;
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
        responseObserver.onNext(creatDoctorOutput(response));
        responseObserver.onCompleted();
    }

    @Override
    public void findById(InputId request, StreamObserver<DoctorOutput> responseObserver) {
        var response = service.findById(request.getId());
        var doctorOutput = creatDoctorOutput(response);
        responseObserver.onNext(doctorOutput);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(InputId request, StreamObserver<EmptyOutput> responseObserver) {
        super.delete(request, responseObserver);
    }

    @Override
    public void findAll(EmptyInput request, StreamObserver<DoctorOutputList> responseObserver) {
        super.findAll(request, responseObserver);
    }

    private DoctorOutput creatDoctorOutput(DoctorResponse doctorResponse) {
        return DoctorOutput.newBuilder()
                .setId(doctorResponse.getId())
                .setName(doctorResponse.getName())
                .setLastName(doctorResponse.getLastName())
                .setAge(doctorResponse.getAge())
                .setSex(doctorResponse.getSex())
                .setSpecialty(doctorResponse.getSpecialty())
                .build();
    }
}
