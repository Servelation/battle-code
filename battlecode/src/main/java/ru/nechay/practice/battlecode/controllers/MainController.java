package ru.nechay.practice.battlecode.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.nechay.practice.battlecode.models.Language;
import ru.nechay.practice.battlecode.models.ProgramTask;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.LanguageRepo;
import ru.nechay.practice.battlecode.repo.UserRepo;

@Controller
public class MainController extends ParentControl{
	@Autowired
	private LanguageRepo languageRepo;
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping(value={"/main", "/"})
	public String main(@AuthenticationPrincipal User user,
					Model model) {
		addUserToModel(user, model);
		List<Language> langs = languageRepo.findAll();

		model.addAttribute("langs", langs);
		
		return "battlecode/main";
	}
	@GetMapping("/rating")
	public String rating(
			@AuthenticationPrincipal User user,
			Model model) {
		addUserToModel(user, model);
		List<User> users = userRepo.findAll()
									.stream()
									.sorted(Comparator.comparing(x -> -x.getExperience()))
									.collect(Collectors.toList());
		model.addAttribute("users",users);
		return "battlecode/rating";
	}
}
