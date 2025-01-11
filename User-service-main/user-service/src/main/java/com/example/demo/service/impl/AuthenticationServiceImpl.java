package com.example.demo.service.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private AuthenticationManager authenticationManager;
	
	private JwtService jwtService;



	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public User signup(SignUpRequest signUpRequest)
	{
		User user=new User();
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setSecondName(signUpRequest.getLastName());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		
		return userRepository.save(user);
	}
	
	public JwtAuthenticationResponse signin(SigninRequest signinRequest)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
		
		var user=userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
		var jwt=jwtService.generateToken(user);
		var refresh=jwtService.generateRefreshToken(new HashMap<>() ,user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refresh);
		
		return jwtAuthenticationResponse;
	}
}
