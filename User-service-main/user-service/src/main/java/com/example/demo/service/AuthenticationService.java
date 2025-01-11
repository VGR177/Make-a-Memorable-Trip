package com.example.demo.service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.entities.User;

public interface AuthenticationService {

	User signup(SignUpRequest signUpRequest);
	
	JwtAuthenticationResponse signin(SigninRequest signinRequest);
}
