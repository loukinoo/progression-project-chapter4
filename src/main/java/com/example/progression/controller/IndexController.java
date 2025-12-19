package com.example.progression.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.progression.service.AuthServices;

@Controller
public class IndexController {
	
	@Autowired
	private AuthServices authServices;

	@GetMapping("/")
	public String redirect() {
		String username = authServices.getLoggedInUsername();
		if (username == null || username.equals("anonymousUser"))
			return "redirect:/login.html";
		return "redirect:/tasks.html";
	}
}
