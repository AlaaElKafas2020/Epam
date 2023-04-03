package com.epam.HelloWorld.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.HelloWorld.Exceptions.ResourceNotFoundException;
import com.epam.HelloWorld.CRUD.Person;
import com.epam.HelloWorld.repos.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new ResourceNotFoundException("Person", "id", id);
        }
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            Person existingPerson = person.get();
            existingPerson.setName(updatedPerson.getName());
            return personRepository.save(existingPerson);
        } else {
            throw new ResourceNotFoundException("Person", "id", id);
        }
    }

    public void deletePerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            personRepository.delete(person.get());
        } else {
            throw new ResourceNotFoundException("Person", "id", id);
        }
    }
}
