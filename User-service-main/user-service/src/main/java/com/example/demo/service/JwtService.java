package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.User;

public interface JwtService {

	String extractUsreName(String token);
	
	String generateToken(UserDetails userDetails);
	
	boolean isTokenValid(String token,UserDetails userDetails);

	String generateRefreshToken(Map<String, Object> extraClaims,UserDetails userDetails);
}
