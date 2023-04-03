package com.epam.HelloWorld;

import com.epam.HelloWorld.Security.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static java.lang.reflect.Array.get;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform((RequestBuilder) MockServerHttpRequest.get("/api/users"))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$[0].id", is(1)))
				.andExpect((ResultMatcher) jsonPath("$[0].username", is("john")))
				.andExpect((ResultMatcher) jsonPath("$[0].email", is("john@example.com")))
				.andExpect((ResultMatcher) jsonPath("$[1].id", is(2)))
				.andExpect((ResultMatcher) jsonPath("$[1].username", is("jane")))
				.andExpect((ResultMatcher) jsonPath("$[1].email", is("jane@example.com")));
	}
}
