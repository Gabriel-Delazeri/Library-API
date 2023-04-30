package br.com.delazeri.library.mapper.person;

import br.com.delazeri.library.person.dtos.v2.PersonDTOV2;
import br.com.delazeri.library.person.entities.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO(Person person) {
        PersonDTOV2 personDTOV2 = new PersonDTOV2();
        personDTOV2.setId(person.getId());
        personDTOV2.setAddress(person.getAddress());
        personDTOV2.setBirthDay(new Date());
        personDTOV2.setFirstName(person.getFirstName());
        personDTOV2.setLastName(person.getLastName());
        personDTOV2.setGender(person.getGender());

        return personDTOV2;
    }

    public Person convertDTOToEntity(PersonDTOV2 personDTOV2) {
        Person person = new Person();
        person.setId(personDTOV2.getId());
        person.setAddress(personDTOV2.getAddress());
//        person.setBirthDay(new Date());
        person.setFirstName(personDTOV2.getFirstName());
        person.setLastName(personDTOV2.getLastName());
        person.setGender(personDTOV2.getGender());

        return person;
    }
}
