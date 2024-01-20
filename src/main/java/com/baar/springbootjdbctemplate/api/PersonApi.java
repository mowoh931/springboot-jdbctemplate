package com.baar.springbootjdbctemplate.api;


import com.baar.springbootjdbctemplate.dto.PersonDto;
import com.baar.springbootjdbctemplate.exception.PersonAlreadtExistsException;
import com.baar.springbootjdbctemplate.exception.PersonNotFoundException;
import com.baar.springbootjdbctemplate.model.Person;
import com.baar.springbootjdbctemplate.service.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonApi {
    private final PersonServiceImpl personService;

    public PersonApi(PersonServiceImpl personService) {
        this.personService = personService;

    }

    @PostMapping(value = "/save/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> savePerson(@RequestBody PersonDto personDto) throws PersonAlreadtExistsException {
        Person savedPerson = personService.savePerson(personDto);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }


    @GetMapping(value = "/get/all/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDto>> getPersons() {
        List<PersonDto> persons = personService.getPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(value = "/get/person/lastName/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findByLastName(@PathVariable(name = "lastName") String lastName) throws PersonNotFoundException {
        Person person = personService.findByLastName(lastName);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping(value = "/update/person/id/email")
    public ResponseEntity<Void> updatePersonEmail(@RequestParam(name = "id") Integer id, @RequestParam(name = "email") String email) throws PersonNotFoundException, PersonNotFoundException {
        personService.updatePersonEmail(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete/person/id/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable(name = "id") Integer id) throws PersonNotFoundException {
        personService.deletePerson(id);
        return new ResponseEntity<>("Delete successfully completed ", HttpStatus.CREATED);
    }

}
