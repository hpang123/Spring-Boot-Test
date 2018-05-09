package hpang.spring.boot.flower.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import hpang.spring.boot.flower.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserClientTest {

	@Value("${user.client.url}")
	private String userClientUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserClient userClient;

	private MockRestServiceServer mockServer;

	@Before
	public void setup() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@After
	public void reset() {
	}

	@Test
	public void shouldGetUniqueUserCount() throws Exception {
		mockServer.expect(once(), requestTo(userClientUrl))
				.andRespond(withSuccess(mockUsersJson(), MediaType.APPLICATION_JSON));

		long uniqueUserIds = userClient.getUniqueUserIds();
		mockServer.verify();
		assertEquals(3, uniqueUserIds);
	}

	@Test
	public void shouldGetModifyUser() throws Exception {
		mockServer.expect(once(), requestTo(userClientUrl))
				.andRespond(withSuccess(mockUsersJson(), MediaType.APPLICATION_JSON));

		User user = userClient.getModifiedUser();
		mockServer.verify();
		assertEquals("1800Flowers", user.getTitle());
		assertEquals("My body has been changed", user.getBody());
	}

	//More tests can be added for special cases like empty result or exception.
	
	private String mockUsersJson() {

		String json = "[{\"userId\": 1,\"id\": 1,\"title\": \"title1\",\"body\": \"body1\"},"
				+ "{\"userId\": 1, \"id\": 2,\"title\": \"title2\",\"body\": \"body2\"},"
				+ "{\"userId\": 2, \"id\": 3,\"title\": \"title3\",\"body\": \"body3\"},"
				+ "{\"userId\": 3, \"id\": 4,\"title\": \"title4\",\"body\": \"body4\"}]";
		return json;
	}
}
