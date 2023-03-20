package br.com.hospital.utils.doctor;

import br.com.hospital.domain.Doctor;
import br.com.hospital.dto.DoctorRequest;
import br.com.hospital.dto.DoctorResponse;

public class DoctorUtilsService {

public static Doctor doctorRequestConverterToDoctor(DoctorRequest request){
    return new Doctor(request);
}

public static DoctorResponse doctorConverterToDoctorResponse(Doctor doctor){
    return new DoctorResponse(doctor);
}

}
