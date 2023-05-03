package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskForm {
	
	@Size(min = 1, max = 255)
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date ;
	
	@Size(min = 1, max = 255)
	private String text;
	

}
