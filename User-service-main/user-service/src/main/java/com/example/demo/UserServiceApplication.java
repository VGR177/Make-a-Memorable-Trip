package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User adminAccount=userRepository.findByRole(Role.ADMIN);
		if(null==adminAccount)
		{
			User user=new User();
			
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setSecondName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepository.save(user);
		}
	}

}
