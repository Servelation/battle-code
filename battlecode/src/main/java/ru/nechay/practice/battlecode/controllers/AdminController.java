package ru.nechay.practice.battlecode.controllers;

import java.util.Arrays;
import java.util.Comparator;
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

import ru.nechay.practice.battlecode.models.Category;
import ru.nechay.practice.battlecode.models.Complexity;
import ru.nechay.practice.battlecode.models.Language;
import ru.nechay.practice.battlecode.models.ProgramTask;
import ru.nechay.practice.battlecode.models.ProposedTask;
import ru.nechay.practice.battlecode.models.Role;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.CategoryRepo;
import ru.nechay.practice.battlecode.repo.ComplexityRepo;
import ru.nechay.practice.battlecode.repo.LanguageRepo;
import ru.nechay.practice.battlecode.repo.ProgramTaskRepo;
import ru.nechay.practice.battlecode.repo.ProposedTaskRepo;
import ru.nechay.practice.battlecode.repo.UserRepo;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProposedTaskRepo proposedTaskRepo;
	
	@Autowired
	private ProgramTaskRepo programTaskRepo;
	
	@Autowired 
	private LanguageRepo languageRepo;
	
	@Autowired 
	private ComplexityRepo complexityRepo;
	
	@Autowired 
	private CategoryRepo categoryRepo;
	
	@GetMapping
	public String getAdmin(Model model) {
		
		return "admin/admin";
	}
	@GetMapping("/users")
	public String userList(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "admin/users";
	}
	
	@PostMapping("/usersdelete")
	public String userDelete(
						@RequestParam("userId") User user) {
		userRepo.delete(user);
		return "redirect:/admin/users";
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
	
	@GetMapping("/proposed")
	public String showProposed(Model model) {
		List<ProposedTask> tasks = proposedTaskRepo.findAll();
		List<Language> languages 		= languageRepo.findAll();
		List<Category> categories 		= categoryRepo.findAll();
		List<Complexity> complexities 	= complexityRepo.findAll();
		model.addAttribute("languages", languages);
		model.addAttribute("categories", categories);
		model.addAttribute("complexities", complexities);
		model.addAttribute("tasks",tasks);
		return "admin/proposed";
	}
	
	@PostMapping("/proposed")
	public String acceptPropoce(
			@RequestParam("action") String action,
			@RequestParam("taskId") ProposedTask task,
			Model model) {
		if(action.equals("accept")) {
			ProgramTask programTask = new ProgramTask(task);
			Long maxId = programTaskRepo
							.findAll()
							.stream()
							.max(Comparator.comparing(x -> x.getId()))
							.get()					
							.getId();
			programTask.setId(maxId + 1);
			programTaskRepo.save(programTask);
			proposedTaskRepo.delete(task);
		}
		if(action.equals("delete")) {
			proposedTaskRepo.delete(task);
		}
		return "redirect:/admin/proposed";
		
	}
}
