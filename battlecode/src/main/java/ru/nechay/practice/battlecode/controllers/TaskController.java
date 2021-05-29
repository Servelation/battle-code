package ru.nechay.practice.battlecode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.nechay.practice.battlecode.models.ProgramTask;
import ru.nechay.practice.battlecode.repo.ProgramTaskRepo;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ProgramTaskRepo programTaskRepo;
	@GetMapping("/{lang}")
	public String showTasks(@PathVariable String lang) {
		List<ProgramTask> tasks = programTaskRepo.findByLanguage(lang);
		tasks.forEach(x -> System.out.println(x));
		return "tasks/list_of_tasks";
	}
}
