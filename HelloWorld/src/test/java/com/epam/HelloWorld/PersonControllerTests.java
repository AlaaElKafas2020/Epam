package com.epam.HelloWorld;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.epam.HelloWorld.Services.PersonService;
import com.epam.HelloWorld.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.epam.HelloWorld.CRUD.Person;


@SpringBootTest
public class PersonControllerTests {

    @Mock
    private PersonService personService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PersonController personController;

    @Test
    public void testCreatePerson() {
        // Arrange
        Person person = new Person();
        person.setId(1L);
        person.setName("Alaa");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(personService.createPerson(any(Person.class))).thenReturn(person);

        // Act
        ResponseEntity<Person> response = personController.createPerson(person, bindingResult);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(person);
    }
}
