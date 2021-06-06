package ru.nechay.practice.battlecode.controllers;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ru.nechay.practice.battlecode.models.Category;
import ru.nechay.practice.battlecode.models.Complexity;
import ru.nechay.practice.battlecode.models.Language;
import ru.nechay.practice.battlecode.models.ProposedTask;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.CategoryRepo;
import ru.nechay.practice.battlecode.repo.ComplexityRepo;
import ru.nechay.practice.battlecode.repo.LanguageRepo;
import ru.nechay.practice.battlecode.repo.ProposedTaskRepo;

@Controller
public class ProposeController extends ParentControl{

	@Autowired
	private LanguageRepo languageRepo;
	
	@Autowired
	private ComplexityRepo complexityRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProposedTaskRepo proposedTaskRepo;
	
	@GetMapping("/propose")
	public String proposePage(
			@AuthenticationPrincipal User user,
			Model model) {
		addUserToModel(user, model);
		List<Language> languages 		= languageRepo.findAll();
		List<Category> categories 		= categoryRepo.findAll();
		List<Complexity> complexities 	= complexityRepo.findAll();
		model.addAttribute("languages", languages);
		model.addAttribute("categories", categories);
		model.addAttribute("complexities", complexities);
		return "propose/propose";
	}
	
	@PostMapping("/propose")
	public String sendPropose(ProposedTask task, Model modelo) {
		proposedTaskRepo.save(task);
		
		return "redirect:/";
	}
}
