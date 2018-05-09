package hpang.spring.boot.flower.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hpang.spring.boot.flower.model.User;

@Component
public class UserClientImpl implements UserClient {
	private static final Logger log = LoggerFactory.getLogger(UserClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user.client.url}")
	private String userClientUrl;

	public long getUniqueUserIds() {

		ResponseEntity<List<User>> UserResponses = getUserResponses();
		if (UserResponses.getStatusCode().value() != 200) {
			log.warn("UserResponse: " + UserResponses.getStatusCode().value());
			return -1;
		}
		List<User> users = UserResponses.getBody();
		if (users.size() == 0) {
			return 0;
		}

		log.debug("User counts: " + users.size());

		long uniqueUserCount = users.stream().map(i -> i.getUserId()).distinct().count();
		return uniqueUserCount;
	}

	public User getModifiedUser() {
		String CHANGED_TITLE = "1800Flowers";
		String CHANGED_BODY = "My body has been changed";

		ResponseEntity<List<User>> UserResponses = getUserResponses();
		if (UserResponses.getStatusCode().value() != 200) {
			log.warn("UserResponse: " + UserResponses.getStatusCode().value());
			return null;
		}
		List<User> users = UserResponses.getBody();
		/* We like to modify 4th element */
		if (users.size() < 4) {
			return null;
		}
		User user = users.get(3);
		user.setTitle(CHANGED_TITLE);
		user.setBody(CHANGED_BODY);
		return user;
	}

	public ResponseEntity<List<User>> getUserResponses() {
		ResponseEntity<List<User>> UserResponses = restTemplate.exchange(userClientUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		return UserResponses;
	}
}
