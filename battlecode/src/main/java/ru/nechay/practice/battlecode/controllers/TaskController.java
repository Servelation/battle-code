package ru.nechay.practice.battlecode.controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.nechay.practice.battlecode.logic.ProgramHandler;
import ru.nechay.practice.battlecode.models.ProgramTask;
import ru.nechay.practice.battlecode.models.User;
import ru.nechay.practice.battlecode.repo.ProgramTaskRepo;
import ru.nechay.practice.battlecode.repo.UserRepo;

@Controller
@RequestMapping("/tasks")
public class TaskController extends ParentControl{

	@Autowired
	private ProgramTaskRepo programTaskRepo;
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/lang/{lang}")
	public String showTasks(
			@AuthenticationPrincipal User user,
			@PathVariable String lang,
			Model model) {
		addUserToModel(user, model);
		List<ProgramTask> tasks = programTaskRepo.findByLanguage(lang);
		tasks.forEach(x -> System.out.println(x));
		model.addAttribute("tasks", tasks);
		if(user!=null) {
			List<ProgramTask> userTasks = userRepo.findAllTasks();
			model.addAttribute("userTasks", userTasks);
		}
		return "tasks/list_of_tasks";
	}
	
	@GetMapping("/{task}")
	public String showTask(
						@AuthenticationPrincipal User user,
						@PathVariable ProgramTask task,
						Model model) {
		addUserToModel(user, model);
		model.addAttribute("task", task);
		model.addAttribute("nextTask", task);
		return "tasks/task";
	}
	
	@PostMapping("/{task}")
	public String inputTask(
					@AuthenticationPrincipal User user,
					@PathVariable ProgramTask task,
					@RequestParam String program,
					Model model) throws IOException {
		addUserToModel(user, model);
		ProgramHandler progHandler = new ProgramHandler(program, task.getInput()); 
		if(!progHandler.isValid()) {
			return "";
		}
        progHandler.redirectionOfStreams();
        progHandler.printProgramToTheFile();
        progHandler.workAtTheShell();
        String outOfTheProgram = progHandler.getOutputOfTheProgram();
        System.out.println(outOfTheProgram);
//        progHandler.flush();
        if(outOfTheProgram.equals(task.getOutput())) {
        	model.addAttribute("success", true);
        	List<Long> userTasks = userRepo.findAllTasks()
        									.stream()
        									.map(x -> x.getId())
        									.collect(Collectors.toList());
        	if(!userTasks.contains(task.getId())) {
        		user.setExperience(user.getExperience() + task.getExperience());
        		user.updateLevel();
        		user.getTasks().add(task);
        		userRepo.save(user);
        	}
        	List<ProgramTask> langTasks = programTaskRepo.findByLanguage("Java")
														.stream()
														.sorted(Comparator.comparing(y -> y.getExperience()))
														.collect(Collectors.toList());
        	ProgramTask nextTask = langTasks.get(0);
        	for(var tk: langTasks) {
        		if(!user.getTasks().contains(tk)) {
        			nextTask = tk;
        			break;
        		}
        	}
        	model.addAttribute("nextTask", nextTask);
        }else {
           	model.addAttribute("nextTask", task);
        	model.addAttribute("fail", true);
        }
        model.addAttribute("out", outOfTheProgram);
		return "tasks/task";
	}
	
}
