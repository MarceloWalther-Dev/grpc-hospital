package br.com.hospital.dto;

import br.com.hospital.domain.Doctor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponse {

    private String id;
    private String name;
    private String lastName;
    private Integer age;
    private String sex;
    private String specialty;

    public DoctorResponse(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.lastName = doctor.getLastName();
        this.age = doctor.getAge();
        this.sex = doctor.getSex();
        this.specialty = doctor.getSpecialty();
    }
}
