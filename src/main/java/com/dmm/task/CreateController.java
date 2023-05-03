package com.dmm.task;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class CreateController {

	@Autowired
	private TasksRepository tasksRepository;

	@GetMapping("/main/create/{date}")
	String Create(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate date, Model model) {

		return "create";

	}

	@PostMapping("/main/create")
	String CreateTask(@Validated TaskForm taskForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		//
		Tasks tasks = new Tasks();

		LocalDate ld = taskForm.getDate();
		String title = taskForm.getTitle();
		String text = taskForm.getText();

		tasks.setDate(ld);
		tasks.setTitle(title);
		tasks.setText(text);
		tasks.setDone(false);
		tasks.setName(user.getName());

		tasksRepository.save(tasks);

		return "redirect:/main";

	}

}
