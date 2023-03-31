package br.com.hospital.service;

import br.com.hospital.dto.DoctorRequest;
import hospital.DoctorOutput;

import java.util.List;

public interface DoctorService {

    DoctorOutput create(DoctorRequest request);

    DoctorOutput findById(String id);

    void delete(String id);

    List<DoctorOutput> findAll();
}
