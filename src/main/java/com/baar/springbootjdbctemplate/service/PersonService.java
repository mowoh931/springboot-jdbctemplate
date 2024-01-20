package com.baar.springbootjdbctemplate.service;


import com.baar.springbootjdbctemplate.dto.PersonDto;
import com.baar.springbootjdbctemplate.exception.PersonAlreadtExistsException;
import com.baar.springbootjdbctemplate.exception.PersonNotFoundException;
import com.baar.springbootjdbctemplate.model.Person;

import java.util.List;

public interface PersonService {
    public Person savePerson(PersonDto personDto) throws PersonAlreadtExistsException;

    public List<PersonDto> getPersons();

    Person findByLastName(String lastName) throws PersonNotFoundException;

    void updatePersonEmail(Integer id, String email) throws PersonNotFoundException;

    void deletePerson(Integer id) throws PersonNotFoundException;

}
