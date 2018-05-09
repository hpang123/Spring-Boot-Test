package hpang.spring.boot.flower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hpang.spring.boot.flower.model.User;
import hpang.spring.boot.flower.model.UserCount;
import hpang.spring.boot.flower.service.UserClient;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserClient userClient;

	@GetMapping("/uniqueids")
	public UserCount getUniqueUserIds() {
		UserCount userCount = new UserCount();
		long uniqueUserCount = userClient.getUniqueUserIds();
		userCount.setUniqueCount(uniqueUserCount);
		return userCount;
	}
	
	@GetMapping("/modify")
	public User getModifiedUser() {
		return userClient.getModifiedUser();
	}
}
