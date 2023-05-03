package com.dmm.task.form;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskEditForm {
	
	@Size(min = 1, max = 255)
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@Size(min = 1, max = 255)
	private LocalDate date ;
	
	@Size(min = 1, max = 255)
	private String text;
	
	private boolean done;
}
