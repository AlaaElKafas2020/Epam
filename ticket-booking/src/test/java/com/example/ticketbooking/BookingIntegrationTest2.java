package com.example.ticketbooking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.activemq.broker-url=tcp://localhost:61616",
        "spring.activemq.user=admin",
        "spring.activemq.password=admin"
})
@EmbeddedActiveMQArtemis
public class BookingIntegrationTest2 {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmbeddedActiveMQBroker embeddedBroker;

    @Test
    public void testBookTicketAsync() throws Exception {
        BookingRequest request = new BookingRequest();
        // Set request properties here

        // Send booking request
        ResponseEntity<Void> response = restTemplate.postForEntity("/bookings", request, Void.class);
       // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Wait for booking message to be processed
        Thread.sleep(1000); // Use a more sophisticated wait strategy if necessary

        // Verify that booking was added to database
        // Use JdbcTemplate or other means to query the database and verify the booking record
    }

}

