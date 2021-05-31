package ru.nechay.practice.battlecode.controllers;

import java.util.List;

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
//	@GetMapping("/rating")
//	public String rating(
//			@AuthenticationPrincipal User user,
//			Model model) {
//		addUserToModel(user, model);
//		model.addAttribute(model)
//		return "battlecode/registration";
//	}
}
