package br.com.hospital.controller;

import br.com.hospital.constantes.Constantes;
import hospital.DoctorInput;
import hospital.DoctorOutput;
import hospital.DoctorServiceGrpc;
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
class DoctorControllerTest extends Constantes {

    public static final String INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO = "INVALID_ARGUMENT: O name não pode estar nulo ou vazio";
    @GrpcClient("inProcess")
    private DoctorServiceGrpc.DoctorServiceBlockingStub serviceBlockingStub;

    @Test
    @DisplayName("when passed complete object must create a doctor")
    void createDoctorSucess(){
        DoctorInput doctorInput = doctorInputBuilder();
        DoctorOutput doctorOutput = serviceBlockingStub.create(doctorInput);
       Assertions.assertThat(doctorInput)
               .usingRecursiveComparison()
               .comparingOnlyFields("name")
               .isEqualTo(doctorOutput);
    }


    @Test
    @DisplayName("when an object with the required attributes is not passed, it must throw an AttributeInValidException")
    void createDoctorExceptionAttributeInValidException(){
        DoctorInput doctorInput = doctorInputBuilderError();
        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(doctorInput))
                .withMessage(INVALID_ARGUMENT_O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO);
    }


    private DoctorInput doctorInputBuilderError(){
        return DoctorInput.newBuilder()
                .setName("")
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build();
    }

    private DoctorInput doctorInputBuilder(){
        return DoctorInput.newBuilder()
                .setName(NAME)
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build();
    }
}