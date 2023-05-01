package br.com.delazeri.library.person.controllers;

import br.com.delazeri.library.person.dtos.v1.PersonDTO;
import br.com.delazeri.library.person.dtos.v2.PersonDTOV2;
import br.com.delazeri.library.person.services.PersonService;
import br.com.delazeri.library.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
        }
    )
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
            value = "{id}",
            produces = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            },
            produces = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        return ResponseEntity.ok().body(service.create(person));
    }

    @PostMapping(
            value = "/v2",
            consumes = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            },
            produces = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    public ResponseEntity<PersonDTOV2> createV2(@RequestBody PersonDTOV2 person) {
        return ResponseEntity.ok().body(service.createV2(person));
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            },
            produces = {
                    MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML
            }
    )
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO person) {
        return ResponseEntity.ok().body(service.update(person));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
