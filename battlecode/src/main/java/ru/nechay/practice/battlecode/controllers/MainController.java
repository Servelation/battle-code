package ru.nechay.practice.battlecode.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.nechay.practice.battlecode.models.Role;
import ru.nechay.practice.battlecode.models.User;

@Controller
public class MainController {

	
	@GetMapping(value={"/main", "/"})
	public String main(@AuthenticationPrincipal User user,
					Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getClass());
		Boolean isAuthificated = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication.getClass());
		System.out.println(isAuthificated);
		
		model.addAttribute("ADMIN", Role.ADMIN);
		model.addAttribute("isAuth",isAuthificated);
		if(user==null) {
			model.addAttribute("level_text","Войдите в аккаунт");
		}else {
			model.addAttribute("level_text", "Level"+Integer.toString(user.getLevel()));
			
		}
		
		model.addAttribute("user", user);
		
		
		
		
		return "battlecode/main";
	}
}
