package br.com.delazeri.library.unittests.mockito.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.delazeri.library.exceptions.RequiredObjectIsNullException;
import br.com.delazeri.library.person.dtos.v1.PersonDTO;
import br.com.delazeri.library.person.entities.Person;
import br.com.delazeri.library.person.repositories.PersonRepository;
import br.com.delazeri.library.person.services.PersonService;
import br.com.delazeri.library.unittests.mockito.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

    void basicAssertions(PersonDTO result) {
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/" + result.getKey() + ">;rel=\"self\"]"));
        assertEquals("Addres Test"+ result.getKey(), result.getAddress());
        assertEquals("First Name Test"+ result.getKey(), result.getFirstName());
        assertEquals("Last Name Test"+ result.getKey(), result.getLastName());
        assertEquals(result.getKey() % 2 == 0 ? "Male" : "Female", result.getGender());
    }

    @Test
    void testFindAll() {
        List<Person> personList = input.mockEntityList();

        when(repository.findAll()).thenReturn(personList);

        List<PersonDTO> personDTOList = service.findAll();

        assertNotNull(personDTOList);
        assertEquals(14, personDTOList.size());

        basicAssertions(personDTOList.get(1));
        basicAssertions(personDTOList.get(3));
    }

    @Test
    void testFindById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        basicAssertions(service.findById(1L));
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

        basicAssertions(service.create(personDTO));
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

        basicAssertions(service.update(personDTO));
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
