package com.example.eventserviceapi;

import com.example.eventservicedto.Event;

import java.util.List;

public interface EventService {
	Event createEvent(Event event);
	Event updateEvent(Event event);
	void deleteEvent(Long id);
	Event getEvent(Long id);
	List<Event> getAllEvents();
	List<Event> getAllEventsByTitle(String title);
}