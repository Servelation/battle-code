package ru.nechay.practice.battlecode.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.nechay.practice.battlecode.models.Role;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.UserRepo;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

	@Autowired
	private UserRepo userRepo;
	@GetMapping
	public String getAdmin(Model model) {
		
		return "admin/admin";
	}
	@GetMapping("/users")
	public String userList(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "admin/users";
	}
	
	@GetMapping("/users/{user}")
	public String userEditForm(@PathVariable User user,
							Model model) {
		model.addAttribute("user", user);
		List<Role> roles = Arrays.asList(Role.values());
		
		roles.forEach(x -> System.out.println(x));
		
		model.addAttribute("roles", roles);
		
		return "admin/user_edit";
	}
	
	@PostMapping("/users")
	public String userSave(
						@RequestParam String username,
						@RequestParam Map<String,String> form,
						@RequestParam("userId") User user) {
		if(!username.isEmpty()) {
			user.setUsername(username);
		}
		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());
		
		
		user.getRoles().clear();
		
		for(String key:form.keySet()) {
			if( roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
			}
		}
		
		userRepo.save(user);
		return "redirect:/admin/users";
	}
}
