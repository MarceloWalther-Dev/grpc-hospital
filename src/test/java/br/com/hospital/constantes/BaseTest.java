package br.com.hospital.constantes;

import br.com.hospital.dto.DoctorRequest;
import hospital.DoctorInput;

public class BaseTest {
    protected static final String LAST_NAME = "walther";
    protected static final String NAME = "Marcelo";
    protected static final int AGE = 35;
    protected static final String MASCULINO = "Masculino";
    protected static final String SPECIALTY = "Prostata";


    protected DoctorRequest doctorRequestBuilder() {
        return new DoctorRequest(DoctorInput.newBuilder()
                .setName(NAME)
                .setLastName(LAST_NAME)
                .setAge(AGE)
                .setSex(MASCULINO)
                .setSpecialty(SPECIALTY)
                .build());
    }
}
