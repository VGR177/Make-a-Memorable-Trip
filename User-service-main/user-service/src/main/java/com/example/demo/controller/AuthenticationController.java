package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.entities.User;
import com.example.demo.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest)
	{
		return ResponseEntity.ok(authenticationService.signup(signUpRequest)
				);
	}
	
//	@PostMapping("/signin")
//	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest)
//	{
//		return ResponseEntity.ok(authenticationService.signin(signinRequest));
//	}
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest)
	{
		return ResponseEntity.ok(authenticationService.signin(signinRequest));
	}

public AuthenticationController(AuthenticationService authenticationService) {
	super();
	this.authenticationService = authenticationService;
}
}
