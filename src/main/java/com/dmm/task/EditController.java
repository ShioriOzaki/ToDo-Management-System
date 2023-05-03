package com.dmm.task;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.dmm.task.data.repository.TasksUpdate;
import com.dmm.task.form.TaskEditForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class EditController {

	@Autowired
	private TasksRepository taskrepo;

	@GetMapping("/main/edit/{id}")

	public String getTaskid(@PathVariable Integer id, Model model) {
		Tasks editTaskslist = taskrepo.findById(id).orElseThrow();
		model.addAttribute("task", editTaskslist);
		return "edit";
	}

	@Autowired
	private TasksUpdate taskupdate;

	@PostMapping("/main/edit/{id}")
	String EditTask(@Validated TaskEditForm taskEditForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model, @PathVariable Integer id) {

		taskupdate.update(user, taskEditForm, id);
		model.addAttribute("date", taskEditForm.getDate());

		return "redirect:/main";
	}

	@PostMapping("/main/delete/{id}")
	public String delete(@PathVariable Integer id) {
		taskrepo.deleteById(id);

		return "redirect:/main";
	}

}
