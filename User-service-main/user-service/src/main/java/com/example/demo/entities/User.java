package com.example.demo.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	
	private String secondName;
	
	private String email;
	
	private String password;
	
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}



//	public Long getId() {
//		return id;
//	}
//
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//
//
//	public String getSecondName() {
//		return secondName;
//	}
//
//
//
//	public void setSecondName(String secondName) {
//		this.secondName = secondName;
//	}
//
//
//
//	public String getEmail() {
//		return email;
//	}
//
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//
//	public Role getRole() {
//		return role;
//	}
//
//
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	
}
