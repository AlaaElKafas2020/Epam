package com.example.ticketbooking;

import org.apache.activemq.artemis.api.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private JmsTemplate jmsTemplate;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.jmsTemplate = new JmsTemplate();
    }

    public Booking createBooking(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        sendBooking(savedBooking);
        return savedBooking;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking savedBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        savedBooking.setName(booking.getName());
        return bookingRepository.save(savedBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public void sendBooking(Booking booking) {
        jmsTemplate.convertAndSend("bookingQueue", booking, message -> {
            message.setStringProperty("bookingId", String.valueOf(booking.getId()));
            return message;
        });
    }
    public void bookTicketAsync(BookingRequest request) {
      //  jmsTemplate.send("bookingQueue", session -> {
        //    Message message = session.createObjectMessage(request);
        //    message.putStringProperty("type", "booking");
        //    return message;
      //  });
    }


    @JmsListener(destination = "bookingQueue")
    public void processBooking(Message message) {
        try {
            if (message.getStringProperty("type").equals("booking")) {
                BookingRequest request = (BookingRequest) ((ObjectMessage) message).getObject();
                // Add appropriate database records here
            }
        } catch (JMSException e) {
            // Handle exception here
        }
    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
