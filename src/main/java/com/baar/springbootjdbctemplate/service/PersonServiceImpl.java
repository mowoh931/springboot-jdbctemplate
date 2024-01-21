package com.baar.springbootjdbctemplate.service;

import com.baar.springbootjdbctemplate.dto.PersonDto;
import com.baar.springbootjdbctemplate.dto.PersonRowMapper;
import com.baar.springbootjdbctemplate.exception.PersonAlreadtExistsException;
import com.baar.springbootjdbctemplate.exception.PersonNotFoundException;
import com.baar.springbootjdbctemplate.model.Person;
import com.baar.springbootjdbctemplate.repository.PersonResository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "personService")
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final JdbcTemplate jdbcTemplate;
    private final PersonResository personResository;

    public PersonServiceImpl(JdbcTemplate jdbcTemplate, PersonResository personResository) {
        this.jdbcTemplate = jdbcTemplate;
        this.personResository = personResository;
    }

    @Override
    public Person savePerson(PersonDto personDto) throws PersonAlreadtExistsException {
        Optional<Person> personFound = personResository.findById(personDto.getId());
        if (personFound.isPresent()) {
            throw new PersonAlreadtExistsException("Person already exists");
        }

        final String createSql = "insert into person values (?,?,?,?,?,?)";

        jdbcTemplate.update(createSql,
                personDto.getId(),
                personDto.getEmail(),
                personDto.getFirstName(),
                personDto.getGender(),
                personDto.getIpAddress(),
                personDto.getLastName());
        log.info("Person created successfully");

        final String fetchSql = "SELECT * from person where id = ?";
        return jdbcTemplate.queryForObject(fetchSql, new Object[]{personDto.getId()}, new PersonRowMapper());
    }

    @Override
    public List<PersonDto> getPersons() {
        ModelMapper modelMapper = new ModelMapper();
        String sql = "SELECT * from person";
        return jdbcTemplate.query(sql, new PersonRowMapper()).stream()
                .map(element -> modelMapper.map(element, PersonDto.class)).toList();
    }

    @Override
    public Person findByLastName(String lastName) throws PersonNotFoundException {
        final String sql = "SELECT * from person where last_name = ?";

        try {
            final Person person = jdbcTemplate.queryForObject(sql, new PersonRowMapper(), lastName);
            log.info("Person with lastname: {} found", lastName);
            return person;
        } catch (Throwable e) {
            log.info("Failed to get person from database with last name : {} ", lastName);
            log.error(e.getMessage());
            throw new PersonNotFoundException("No such person");
        }

    }

    @Override
    public void updatePersonEmail(Integer id, String email) throws PersonNotFoundException {
        final String fetchSql = "select * from person where id = ?";
        final Person personFound = jdbcTemplate.queryForObject(fetchSql, new Object[]{id}, new PersonRowMapper());
        if (personFound == null) {
            throw new PersonNotFoundException("No such person");
        } else {
            final String oldEmail = personFound.getEmail();
            String updateSql = "UPDATE person SET email = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, email, id);
            log.info("Updated person email: {} {} {}", oldEmail, " >>>>> ", email);
        }
    }

    @Override
    public void deletePerson(Integer id) throws PersonNotFoundException {
        Person personFound = personResository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("No such person"));
        String deleteSql = " delete from person where id = ?";
        jdbcTemplate.update(deleteSql, id);
        log.info("Delete successful : {}", personFound.getEmail());

    }
}
