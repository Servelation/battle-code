package ru.nechay.practice.battlecode.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.nechay.practice.battlecode.models.User;

@Controller
public class UserController extends ParentControl{

	@GetMapping("/user/{user}/edit")
	public String editUser(@PathVariable User user,
						@AuthenticationPrincipal User realUser,
						Model model) {
		addUserToModel(user, model);
		model.addAttribute("link_user", user);
		if(user.equals(realUser)) {
			return "user/user";
		}else {
			return "user/not_access";
		}
		
		
	}
}
