package com.epam.HelloWorld.controller;

import java.util.List;

import com.epam.HelloWorld.Exceptions.InvalidRequestException;
import com.epam.HelloWorld.Exceptions.ResourceNotFoundException;
import com.epam.HelloWorld.Services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.epam.HelloWorld.CRUD.Person;
import com.epam.HelloWorld.repos.PersonRepository;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // CREATE operation
    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid create person request", bindingResult);
        }
        PersonService personService = new PersonService();
        Person createdPerson = personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }


    // READ operation
    @GetMapping("")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personRepository.findAll();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    // READ operation
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    // UPDATE operation
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId,
                                               @Valid @RequestBody Person personDetails) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
        person.setName(personDetails.getName());
        final Person updatedPerson = personRepository.save(person);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    // DELETE operation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
        personRepository.delete(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
