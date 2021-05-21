package ru.nechay.practice.battlecode.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ru.nechay.practice.battlecode.models.Role;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.UserRepo;

@Controller
public class RegistrationController {
	@Autowired
	private UserRepo userRepo;
	
	
	@GetMapping("/registration")
	public String registration() {
		
		return "battlecode/registration";
	}
	
	
	@PostMapping("/registration")
	public String addUser(User user, Model model) {
		User userFromDb = userRepo.findByUsername(user.getUsername());
		
		if(userFromDb != null) {
			model.addAttribute("messageExists", "User exists!");
			return "battlecode/registration";
		}
		
		user.setActive(true);
		user.setExperience(0);
		user.setLevel(1);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);
		
		return "redirect:/login";
	}
}
