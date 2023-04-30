package br.com.delazeri.library.person.services;

import br.com.delazeri.library.exceptions.ResourceNotFoundException;
import br.com.delazeri.library.mapper.config.DozerMapper;
import br.com.delazeri.library.mapper.person.PersonMapper;
import br.com.delazeri.library.person.dtos.v1.PersonDTO;
import br.com.delazeri.library.person.dtos.v2.PersonDTOV2;
import br.com.delazeri.library.person.entities.Person;
import br.com.delazeri.library.person.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonDTO> findAll() {
        return DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return DozerMapper.parseObject(person, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = DozerMapper.parseObject(personDTO, Person.class);
        return DozerMapper.parseObject(repository.save(person), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 personDTO) {
        Person person = mapper.convertDTOToEntity(personDTO);
        return mapper.convertEntityToDTO(person);
    }

    public PersonDTO update(PersonDTO personDTO) {
        Person person = repository.findById(personDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        person.setFirstName(person.getFirstName());
        person.setLastName(person.getLastName());
        person.setAddress(person.getAddress());
        person.setGender(person.getGender());

        return DozerMapper.parseObject(repository.save(person), PersonDTO.class);
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
