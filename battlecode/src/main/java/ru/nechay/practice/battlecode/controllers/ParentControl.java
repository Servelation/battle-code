package ru.nechay.practice.battlecode.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import ru.nechay.practice.battlecode.models.User;

public class ParentControl {
	
	
	public void addUserToModel(User user, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Boolean isAuthificated = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication.getClass());
		
		model.addAttribute("isAuth",isAuthificated);
		if(user==null) {
			model.addAttribute("level_text","Войдите в аккаунт");
		}else {
			model.addAttribute("level_text", "Level: "+Integer.toString(user.getLevel()));
			
		}
		model.addAttribute("user", user);
	}
}
