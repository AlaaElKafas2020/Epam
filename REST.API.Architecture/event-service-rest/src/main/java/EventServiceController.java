import com.example.eventserviceapi.EventService;
import com.example.eventservicedto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventServiceController {

	@Autowired
	private EventService eventService;

	@PostMapping
	public Event createEvent(@RequestBody Event event) {
		return eventService.createEvent(event);
	}

	@PutMapping("/{id}")
	public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
		event.setId(id);
		return eventService.updateEvent(event);
	}

	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable Long id) {
		eventService.deleteEvent(id);
	}

	@GetMapping("/{id}")
	public Event getEvent(@PathVariable Long id) {
		return eventService.getEvent(id);
	}

	@GetMapping
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping(params = "title")
	public List<Event> getAllEventsByTitle(@RequestParam String title) {
		return eventService.getAllEventsByTitle(title);
	}
}