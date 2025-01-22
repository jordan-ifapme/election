package be.ifapme.election.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonneAPI {
    private String eid;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String gender;
}
