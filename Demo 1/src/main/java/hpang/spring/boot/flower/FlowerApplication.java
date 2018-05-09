package hpang.spring.boot.flower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import hpang.spring.boot.flower.model.User;
import hpang.spring.boot.flower.service.UserClient;

@SpringBootApplication
public class FlowerApplication {
	private static final Logger log = LoggerFactory.getLogger(FlowerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(FlowerApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(UserClient userClient) throws Exception {
		return args -> {
			User user = userClient.getModifiedUser();
		};
	}
}
