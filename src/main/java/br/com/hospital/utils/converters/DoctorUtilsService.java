package br.com.hospital.utils.converters;

import br.com.hospital.domain.Doctor;
import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;

public class DoctorUtilsService {

    private DoctorUtilsService() {
    }

    public static Doctor doctorRequestConverterToDoctor(DoctorRequest request){
    return new Doctor(request);
}

public static DoctorResponse doctorConverterToDoctorResponse(Doctor doctor){
    return new DoctorResponse(doctor);
}

}
