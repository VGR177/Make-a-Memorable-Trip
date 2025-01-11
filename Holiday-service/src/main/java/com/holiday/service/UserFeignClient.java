package com.holiday.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userService")
public interface UserFeignClient 
{
	@GetMapping("/api/users/{id}")
	Object getUserById(@PathVariable Long id);

}
