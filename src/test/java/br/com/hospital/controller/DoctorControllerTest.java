package br.com.hospital.controller;

import br.com.hospital.constantes.BaseTest;
import hospital.DoctorInput;
import hospital.DoctorOutput;
import hospital.DoctorServiceGrpc;
import hospital.InputId;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
class DoctorControllerTest extends BaseTest {

    public static final String INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO = "INVALID_ARGUMENT: O name não pode estar nulo ou vazio";
    @GrpcClient("inProcess")
    private DoctorServiceGrpc.DoctorServiceBlockingStub serviceBlockingStub;

    @Test
    @DisplayName("when passed complete object must create a doctor")
    void createDoctorSucess() {
        DoctorInput doctorInput = doctorInputBuilder();
        DoctorOutput doctorOutput = serviceBlockingStub.create(doctorInput);
        Assertions.assertThat(doctorInput)
                .usingRecursiveComparison()
                .comparingOnlyFields("name")
                .isEqualTo(doctorOutput);
    }


    @Test
    @DisplayName("when an object with the required attributes is not passed, it must throw an AttributeInValidException")
    void createDoctorExceptionAttributeInValidException() {
        DoctorInput doctorInput = doctorInputBuilderError();
        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(doctorInput))
                .withMessage(INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO);
    }


    @Test
    @DisplayName("when passed a valid id it should return a doctor")
    void findByIdSuccess() {
        var inPutId = InputId.newBuilder().setId("c89cc43d-1218-4fca-8a6c-a0886644fda2").build();
        var doctorOutput = serviceBlockingStub.findById(inPutId);
        Assertions.assertThat(doctorOutput)
                .extracting("name", "lastName", "age", "sex", "specialty")
                .contains("Marcelo2", "Waltherr", 35, "Masculino", "Prostata");
    }


    @Test
    @DisplayName("when passed a null id it should throw an DoctorNotFoundException")
    void findByIdDoctorNotFoundException() {
        var inPutId = InputId.newBuilder().setId("").build();
        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findById(inPutId))
                .withMessage(INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO);
    }


    @Test
    @DisplayName("when passed a null id it should throw an AttributeInValidException")
    void findByIdAttributeInValidException() {
        //TODO request não aceita null, se passado null id ='', se forçar null no input joga nullPointerException
        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findById(null))
                .withMessage(INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO);
    }

    @Test
    @DisplayName("when passed a valid id must delete doctor successfully")
    void deleteDoctorSucess() {
        InputId inputId = InputId.newBuilder().setId("6425e730cdd8420700aa0718").build();
        Assertions.assertThatNoException().isThrownBy(() -> serviceBlockingStub.delete(inputId));
    }


    @Test
    @DisplayName("when id is not passed it must throw an exception")
    void deleteDoctorException() {
        InputId inputId = InputId.newBuilder().setId("").build();
        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.delete(inputId))
                .withMessage("INVALID_ARGUMENT: O id não pode estar nulo ou vazio");
    }


    private DoctorInput doctorInputBuilderError() {
        return DoctorInput.newBuilder()
                .setName("")
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build();
    }

    private DoctorInput doctorInputBuilder() {
        return DoctorInput.newBuilder()
                .setName(NAME)
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build();
    }
}