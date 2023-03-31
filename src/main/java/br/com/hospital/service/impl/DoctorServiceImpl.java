package br.com.hospital.service.impl;

import br.com.hospital.domain.Doctor;
import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;
import br.com.hospital.exceptions.AttributeInValidException;
import br.com.hospital.exceptions.DoctorNotFoundException;
import br.com.hospital.repository.DoctorRepository;
import br.com.hospital.service.DoctorService;
import br.com.hospital.utils.converters.DoctorUtilsService;
import hospital.DoctorOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Override
    public DoctorOutput create(DoctorRequest request) {
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(request);
        DoctorResponse doctorResponse = DoctorUtilsService.doctorConverterToDoctorResponse(repository.save(doctor));
        return creatDoctorOutput(doctorResponse);
    }

    @Override
    public DoctorOutput findById(String id) {
        if (Objects.nonNull(id) && !id.isEmpty()) {
            var doctor = repository.findById(id).orElseThrow(() -> new DoctorNotFoundException(String.format("Não foi encontrado nenhum doctor com id %s", id)));
            DoctorResponse doctorResponse = DoctorUtilsService.doctorConverterToDoctorResponse(doctor);
            return creatDoctorOutput(doctorResponse);
        }
        throw new AttributeInValidException("O id não pode estar nulo ou vazio");
    }

    @Override
    public void delete(String id) {
        if (Objects.nonNull(id) && !id.isEmpty()) {
            var doctor = repository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Não existe doutor com esse id"));
            repository.delete(doctor);
            return;
        }
        throw new AttributeInValidException("O id não pode estar nulo ou vazio");
    }

    @Override
    public List<DoctorOutput> findAll() {
        List<Doctor> doctors = repository.findAll();
        return doctors.stream()
                .map(DoctorUtilsService::doctorConverterToDoctorResponse)
                .collect(Collectors.toList())
                .stream()
                .map(this::creatDoctorOutput)
                .collect(Collectors.toList());
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
