package com.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jwt.repository.UserRepository;
import com.jwt.entity.User;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// 사용자 추가
			if (userRepository.findByUsername("user").isEmpty()) {
				User user = new User();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("aa24d035-c970-4997-9eaa-90ae20585df9")); // 비밀번호 암호화
				userRepository.save(user);
			}
		};
	}
}
