package br.com.hospital.service;

import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;

public interface DoctorService {

    DoctorResponse create(DoctorRequest request);

    DoctorResponse findById(String id);

    void delete(String id);

}
