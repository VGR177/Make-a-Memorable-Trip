package com.example.demo.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private  UserRepository userRepository;
	
	
	public UserDetailsService userDetailsService()
	{
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userRepository.findByEmail(username)
						.orElseThrow(()-> new UsernameNotFoundException("User not found"));
			}
		};
	}


	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
}
