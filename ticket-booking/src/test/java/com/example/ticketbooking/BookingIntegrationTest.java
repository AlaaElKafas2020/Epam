package com.example.ticketbooking;

import org.apache.activemq.artemis.api.core.Message;
import org.apache.activemq.artemis.jms.client.ActiveMQObjectMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.ObjectMessage;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.activemq.broker-url=vm://localhost",
        "spring.activemq.in-memory=true"
})
public class BookingIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    public void testBookTicketAsync() throws Exception {
        BookingRequest request = new BookingRequest();
        // Set request properties here

        // Send booking request
        ResponseEntity<Void> response = restTemplate.postForEntity("/bookings", request, Void.class);
      //  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify that booking message was sent to JMS queue
        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        //verify(jmsTemplate).send((Destination) eq("bookingQueue"), argument.capture());
        BookingRequest capturedRequest = (BookingRequest) ((ObjectMessage) argument.getValue()).getObject();
       // assertThat(capturedRequest).isEqualTo(request);
        // Simulate booking message processing
       //Message message = new ActiveMQObjectMessage();
        //((ActiveMQObjectMessage) message).setObject(request);
        //new BookingService((BookingRepository) jmsTemplate).processBooking(message);

        // Verify that booking was added to database
        // Use JdbcTemplate or other means to query the database and verify the booking record
    }

}
