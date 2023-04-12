package com.example.eventserviceimpl;

import com.example.eventservicedto.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

	Event save(Event event);

	void deleteById(Long id);

	Optional<Event> findById(Long id);

	List<Event> findAll();

	List<Event> findByTitleContainingIgnoreCase(String title);
}