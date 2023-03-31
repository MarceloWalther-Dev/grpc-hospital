package br.com.hospital.service.impl;


import br.com.hospital.constantes.BaseTest;
import br.com.hospital.domain.Doctor;
import br.com.hospital.exceptions.AttributeInValidException;
import br.com.hospital.exceptions.DoctorNotFoundException;
import br.com.hospital.repository.DoctorRepository;
import br.com.hospital.utils.converters.DoctorUtilsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest extends BaseTest {

    public static final String ID = "123456789";
    @InjectMocks
    private DoctorServiceImpl service;

    @Mock
    private DoctorRepository repository;

    @Captor
    private ArgumentCaptor<Doctor> doctorCaptor;

    @Test
    @DisplayName("when a correct doctor is passed, it must create a record in the bank")
    void createSucess() {
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(doctorRequestBuilder());
        doctor.setId(ID);
        var doctorResponse = DoctorUtilsService.doctorConverterToDoctorResponse(doctor);
        Mockito.when(repository.save(doctorCaptor.capture())).thenReturn(doctor);
        var response = service.create(doctorRequestBuilder());
        assertThat(response).usingRecursiveComparison().isEqualTo(doctorResponse);
    }


    @Test
    @DisplayName("when passed an id must find a doctor at the bank")
    void findByIdSucess() {
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(doctorRequestBuilder());
        var id = ID;
        doctor.setId(ID);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(doctor));
        var doctorResponse = DoctorUtilsService.doctorConverterToDoctorResponse(doctor);
        var response = service.findById(id);
        assertThat(response).usingRecursiveComparison().isEqualTo(doctorResponse);
    }


    @Test
    @DisplayName("when there is no doctor with past id, it should return an exception of doctor not found")
    void findByIdDoctorNotFoundException() {
        Assertions.assertThatExceptionOfType(DoctorNotFoundException.class)
                .isThrownBy(() -> service.findById(ID))
                .withMessage("Não foi encontrado nenhum doctor com id 123456789");
    }


    @Test
    @DisplayName("when passed a null or empty id it should throw AttributeInvalidException")
    void findByIdNullAttributeInValidException() {
        String id = null;
        Assertions.assertThatExceptionOfType(AttributeInValidException.class)
                .isThrownBy(() -> service.findById(id))
                .withMessage("O id não pode estar nulo ou vazio");
    }

    @Test
    @DisplayName("when passed a null or empty id it should throw AttributeInvalidException")
    void findByIdEmptyAttributeInValidException() {
        String id = "";
        Assertions.assertThatExceptionOfType(AttributeInValidException.class)
                .isThrownBy(() -> service.findById(id))
                .withMessage("O id não pode estar nulo ou vazio");
    }

    @Test
    @DisplayName("when passed a valid id deleted doctor")
    void deleteDoctorToId() {
        var id = "a6a3c126-eaa6-4dac-ac36-ffeb97967dc9";
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(doctorRequestBuilder());
        doctor.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(doctor));
        Mockito.doNothing().when(repository).delete(doctor);
        service.delete(id);
        Mockito.verify(repository).findById(id);
    }


    @Test
    @DisplayName("when passed an invalid id it must throw an exception")
    void deleteDoctorToIdExceptionAttributeInValidException() {
        String id = "";
        Assertions.assertThatExceptionOfType(AttributeInValidException.class)
                .isThrownBy(() -> service.delete(id))
                .withMessage("O id não pode estar nulo ou vazio");
    }


    @Test
    @DisplayName("when passed an invalid id it must throw an exception")
    void deleteDoctorToIdExceptionDoctorNotFoundException() {
        String id = "1234567890";
        Assertions.assertThatExceptionOfType(DoctorNotFoundException.class)
                .isThrownBy(() -> service.delete(id))
                .withMessage("Não existe doutor com esse id");
    }


    @Test
    @DisplayName("must return all doctors")
    void findAllSucess() {
        var doctor = DoctorUtilsService.doctorRequestConverterToDoctor(doctorRequestBuilder());
        doctor.setId("1234567890");
        List<Doctor> doctorList = List.of(doctor);
        Mockito.when(repository.findAll()).thenReturn(doctorList);
        service.findAll();
        Mockito.verify(repository).findAll();
    }

}