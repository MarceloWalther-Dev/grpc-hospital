package br.com.hospital.domain;

import br.com.hospital.dto.DoctorRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    private String id;
    private String name;
    private String lastName;
    private Integer age;
    private String sex;

    private String specialty;

    public Doctor(DoctorRequest request) {
        this.name = request.getName();
        this.lastName = request.getLastName();
        this.age = request.getAge();
        this.sex = request.getSex();
        this.specialty = request.getSpecialty();
    }
}
