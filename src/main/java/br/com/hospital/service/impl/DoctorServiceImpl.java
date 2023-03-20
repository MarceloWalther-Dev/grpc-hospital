package br.com.hospital.service.impl;

import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;
import br.com.hospital.exceptions.AttributeInValidException;
import br.com.hospital.exceptions.DoctorNotFoundException;
import br.com.hospital.repository.DoctorRepository;
import br.com.hospital.service.DoctorService;
import br.com.hospital.utils.doctor.DoctorUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Override
    public DoctorResponse create(DoctorRequest request) {
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(request);
        return DoctorUtilsService.doctorConverterToDoctorResponse(repository.save(doctor));
    }

    @Override
    public DoctorResponse findById(String id) {
        if (Objects.nonNull(id)) {
            var doctor = repository.findById(id).orElseThrow(() -> new DoctorNotFoundException(String.format("Não foi encontrado nenhum doctor com id %s", id)));
            return DoctorUtilsService.doctorConverterToDoctorResponse(doctor);
        }
        throw new AttributeInValidException("O id não pode estar nulo ou vazio");

    }
}
