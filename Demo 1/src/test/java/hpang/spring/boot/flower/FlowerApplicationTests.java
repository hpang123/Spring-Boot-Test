package hpang.spring.boot.flower;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import hpang.spring.boot.flower.model.User;
import hpang.spring.boot.flower.model.UserCount;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FlowerApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUniqueUserCountVanillaService() {
		RestTemplate restTemplate = new RestTemplate();

		UserCount userCount = restTemplate.getForObject("http://localhost:" + port + "/user/uniqueids",
				UserCount.class);
		assertNotNull(userCount);

	}

	@Test
	public void testModifyUserVanillaService() {
		RestTemplate restTemplate = new RestTemplate();

		User user = restTemplate.getForObject("http://localhost:" + port + "/user/modify", User.class);
		assertNotNull(user);
		assertEquals(user.getTitle(), "1800Flowers");
		assertEquals(user.getBody(), "My body has been changed");
	}
}
