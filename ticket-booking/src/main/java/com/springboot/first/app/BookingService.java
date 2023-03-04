package com.springboot.first.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookingService {

    private JmsTemplate jmsTemplate;
    private MessageConverter messageConverter;
    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    public BookingService() {
        this.jmsTemplate = new JmsTemplate();
        this.messageConverter = new SimpleMessageConverter();
    }

    @JmsListener(destination = "bookingQueue")
    public void processBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void sendBooking(Booking booking) {
        jmsTemplate.convertAndSend("bookingQueue", booking);
    }

    public Booking getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking booking = getBookingById(id);
        booking.setName(updatedBooking.getName());
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}