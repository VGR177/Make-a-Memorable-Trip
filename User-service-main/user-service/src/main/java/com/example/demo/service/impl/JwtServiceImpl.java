package com.example.demo.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	public String generateToken(UserDetails userDetails)
	{
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getSigninKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsreName(String token)
	{
		return extractClaims(token, Claims::getSubject);
	}
	
	private <T> T extractClaims(String token,Function<Claims,T> claimsResolvers)
	{
		final Claims cliams=extractAllClaims(token);
		return claimsResolvers.apply(cliams);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSigninKey() {
		byte[] key=Decoders.BASE64.decode("413F44284728482506553685660597033733676397924422645294840406351");
		return Keys.hmacShaKeyFor(key);
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails)
	{
		final String username=extractUsreName(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}

	@Override
	public String generateRefreshToken(Map<String, Object> extraClaims,UserDetails userDetails) {
		
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+604800000))
				.signWith(getSigninKey(),SignatureAlgorithm.HS256)
				.compact();
	}
}
