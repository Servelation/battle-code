package ru.nechay.practice.battlecode.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	
	@GetMapping("/main")
	public String main(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getClass());
		Boolean isAuthificated = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication.getClass());
		System.out.println(isAuthificated);
		model.addAttribute("isAuth",isAuthificated);
		return "battlecode/main";
	}
}
