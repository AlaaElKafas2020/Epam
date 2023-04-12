import com.example.eventserviceapi.EventService;
import com.example.eventservicedto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private com.example.eventserviceimpl.EventRepository eventRepository;

	@Override
	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public Event updateEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	@Override
	public Event getEvent(Long id) {
		return eventRepository.findById(id).orElse(null);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	@Override
	public List<Event> getAllEventsByTitle(String title) {
		return eventRepository.findByTitleContainingIgnoreCase(title);
	}
}