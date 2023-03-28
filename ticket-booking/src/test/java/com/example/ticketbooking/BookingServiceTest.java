package com.example.ticketbooking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private JmsTemplate jmsTemplate;

    @MockBean
    private MessageConverter messageConverter;

    @BeforeEach
    void setUp() {
        bookingService.setJmsTemplate(jmsTemplate);
    }

    @Test
    public void testAsyncBooking() {
        Booking booking = new Booking("Alaa","enginneering");
        booking.setName("John Doe");

        bookingService.sendBooking(booking);

        Booking savedBooking = bookingService.getBookingById(booking.getId());
        assertNotNull(savedBooking);
        assertEquals(booking.getName(), savedBooking.getName());
    }
}


