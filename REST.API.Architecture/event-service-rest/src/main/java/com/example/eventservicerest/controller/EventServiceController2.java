package com.example.eventservicerest.controller;

import java.util.List;

import com.example.eventserviceapi.EventService;
import com.example.eventservicedto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/events")
public class EventServiceController2 {

	@Autowired
	private EventService eventService;

	@ApiOperation(value = "Create an event", response = Event.class)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> createEvent(@ApiParam(value = "Event data", required = true) @RequestBody  Event event) {
		Event createdEvent = eventService.createEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
	}

	@ApiOperation(value = "Update an event", response = Event.class)
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> updateEvent(@ApiParam(value = "Event ID", required = true) @PathVariable String id,
			@ApiParam(value = "Event data", required = true) @RequestBody Event event) {
		Event updatedEvent = eventService.updateEvent(event);
		return ResponseEntity.ok(updatedEvent);
	}

	@ApiOperation(value = "Get all events", response = List.class)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Event>> getAllEvents() {
		List<Event> events = eventService.getAllEvents();
		return ResponseEntity.ok(events);
	}

	@ApiOperation(value = "Get all events by title", response = List.class)
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Event>> getAllEventsByTitle(@ApiParam(value = "Event title", required = true) @RequestParam String title) {
		List<Event> events = eventService.getAllEventsByTitle(title);
		return ResponseEntity.ok(events);
	}

}