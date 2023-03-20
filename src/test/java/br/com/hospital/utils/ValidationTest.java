package br.com.hospital.utils;

import br.com.hospital.constantes.Constantes;
import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.exceptions.AttributeInValidException;
import hospital.DoctorInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationTest extends Constantes {


    public static final String O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO = "O name não pode estar nulo ou vazio";

    @Test
    @DisplayName("when passed complete object should not throw exception")
    void validateRequiredAttributes() {
        var doctorRequest = doctorRequestBuilder();
        Validation.validateRequiredAttributes(doctorRequest);
    }

    @Test
    @DisplayName("when a mandatory attribute is not passed, exception Attribute invalid must be thrown")
    void validateRequiredAttributesAttributeInvalidException() {
        var doctorRequest = doctorRequestBuilder();
        doctorRequest.setName("");
        Assertions.assertThatExceptionOfType(AttributeInValidException.class)
                .isThrownBy(() -> Validation.validateRequiredAttributes(doctorRequest))
                .withMessage(O_NAME_NAO_PODE_ESTAR_NULO_OU_VAZIO);
    }

    private DoctorRequest doctorRequestBuilder() {
        return new DoctorRequest(DoctorInput.newBuilder()
                .setName(NAME)
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build());
    }

}