package br.com.delazeri.library.person.services;

import br.com.delazeri.library.exceptions.RequiredObjectIsNullException;
import br.com.delazeri.library.exceptions.ResourceNotFoundException;
import br.com.delazeri.library.mapper.config.DozerMapper;
import br.com.delazeri.library.mapper.person.PersonMapper;
import br.com.delazeri.library.person.controllers.PersonController;
import br.com.delazeri.library.person.dtos.v1.PersonDTO;
import br.com.delazeri.library.person.dtos.v2.PersonDTOV2;
import br.com.delazeri.library.person.entities.Person;
import br.com.delazeri.library.person.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonDTO> findAll() {
        List<PersonDTO> personDTOList = DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);

        personDTOList.forEach(
                personDTO -> personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel())
        );

        return personDTOList;
    }

    public PersonDTO findById(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        PersonDTO personDTO = DozerMapper.parseObject(person, PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personDTO;
    }

    public PersonDTO create(PersonDTO personDTO) {

        if (personDTO == null) throw new RequiredObjectIsNullException();

        Person person = DozerMapper.parseObject(personDTO, Person.class);
        personDTO = DozerMapper.parseObject(repository.save(person), PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
        return personDTO;
    }

    public PersonDTOV2 createV2(PersonDTOV2 personDTO) {
        Person person = mapper.convertDTOToEntity(personDTO);
        return mapper.convertEntityToDTO(person);
    }

    public PersonDTO update(PersonDTO personDTO) {

        if (personDTO == null) throw new RequiredObjectIsNullException();

        Person person = repository.findById(personDTO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        person.setFirstName(person.getFirstName());
        person.setLastName(person.getLastName());
        person.setAddress(person.getAddress());
        person.setGender(person.getGender());

        personDTO = DozerMapper.parseObject(repository.save(person), PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
        return personDTO;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
