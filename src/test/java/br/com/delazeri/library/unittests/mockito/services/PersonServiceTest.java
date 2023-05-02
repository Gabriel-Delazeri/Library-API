package br.com.delazeri.library.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.delazeri.library.exceptions.RequiredObjectIsNullException;
import br.com.delazeri.library.person.dtos.v1.PersonDTO;
import br.com.delazeri.library.person.entities.Person;
import br.com.delazeri.library.person.repositories.PersonRepository;
import br.com.delazeri.library.person.services.PersonService;
import br.com.delazeri.library.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Person> personList = input.mockEntityList();

        when(repository.findAll()).thenReturn(personList);

        List<PersonDTO> personDTOList = service.findAll();

        assertNotNull(personDTOList);
        assertEquals(14, personDTOList.size());

        PersonDTO result1 = personDTOList.get(1);
        assertNotNull(result1);
        assertNotNull(result1.getKey());
        assertNotNull(result1.getLinks());
        assertTrue(result1.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result1.getAddress());
        assertEquals("First Name Test1", result1.getFirstName());
        assertEquals("Last Name Test1", result1.getLastName());
        assertEquals("Female", result1.getGender());

        PersonDTO result4 = personDTOList.get(4);
        assertNotNull(result4);
        assertNotNull(result4.getKey());
        assertNotNull(result4.getLinks());
        assertTrue(result4.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
        assertEquals("Addres Test4", result4.getAddress());
        assertEquals("First Name Test4", result4.getFirstName());
        assertEquals("Last Name Test4", result4.getLastName());
        assertEquals("Male", result4.getGender());
    }

    @Test
    void testFindById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PersonDTO personDTO = service.findById(1L);
        assertNotNull(personDTO);
        assertNotNull(personDTO.getKey());
        assertNotNull(personDTO.getLinks());
        assertTrue(personDTO.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", personDTO.getAddress());
        assertEquals("First Name Test1", personDTO.getFirstName());
        assertEquals("Last Name Test1", personDTO.getLastName());
        assertEquals("Female", personDTO.getGender());
    }

    @Test
    void testCreate() {
        Person person = input.mockEntity(1);

        Person persistedPerson = person;
        persistedPerson.setId(1L);

        PersonDTO personDTO = input.mockDTO(1);
        personDTO.setKey(1L);

        when(repository.save(person))
                .thenReturn(persistedPerson);

        PersonDTO result = service.create(personDTO);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdate() {
        Person person = input.mockEntity(1);

        person.setId(1L);

        PersonDTO personDTO = input.mockDTO(1);
        personDTO.setKey(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(person));
        when(repository.save(person))
                .thenReturn(person);

        PersonDTO result = service.update(personDTO);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}
