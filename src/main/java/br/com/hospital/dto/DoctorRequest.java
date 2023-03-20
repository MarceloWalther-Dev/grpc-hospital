package br.com.hospital.dto;


import hospital.DoctorInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {
    private String name;
    private String lastName;
    private Integer age;
    private String sex;
    private String specialty;

    public DoctorRequest(DoctorInput doctorInput) {
        this.name = doctorInput.getName();
        this.lastName = doctorInput.getLastName();
        this.age = doctorInput.getAge();
        this.sex = doctorInput.getSex();
        this.specialty = doctorInput.getSpecialty();
    }
}
