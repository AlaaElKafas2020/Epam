package com.epam.HelloWorld.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.epam.HelloWorld.CRUD.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}