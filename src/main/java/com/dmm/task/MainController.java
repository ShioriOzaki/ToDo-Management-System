package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.CalcWeek;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class MainController {

	@Autowired
	private TasksRepository taskrepo;

	@GetMapping("/main")

	public String getCalender(@AuthenticationPrincipal AccountUserDetails user,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,Model model) {

		
		LocalDate loday = null;
		if (date == null) {

			LocalDate now = LocalDate.now();
			loday = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
			LocalDate prevdate = loday.minusMonths(1);
			LocalDate nextdate = loday.plusMonths(1);
			model.addAttribute("prev", prevdate);
			model.addAttribute("next", nextdate);
			
		} else {

			loday = date;
			LocalDate prevdate = loday.minusMonths(1);
			LocalDate nextdate = loday.plusMonths(1);
			model.addAttribute("prev", prevdate);
			model.addAttribute("next", nextdate);
		}

		List<LocalDate> week = new ArrayList<>();
		List<List<LocalDate>> matrix = new ArrayList<>();

		// LocalDateTime now = LocalDateTime.now();

		// LocalDate loday = LocalDate.of(now.getYear(), now.getMonthValue(), 1);

		int lastday = loday.lengthOfMonth();
		LocalDate nextmonth =  LocalDate.of(loday.getYear(), loday.getMonthValue(), lastday);
		DayOfWeek nextdayweek = nextmonth.getDayOfWeek();
		CalcWeek calw = new CalcWeek();
		int weeknum = calw.weekcal(nextdayweek);
		int dayofnum = 7 - weeknum;

		lastday = lastday + dayofnum;

		String dayofMonth = loday.format(DateTimeFormatter.ofPattern("yyyy年MM月"));

		while (true) {

			DayOfWeek lodayweek = loday.getDayOfWeek();

			if (lodayweek == DayOfWeek.SUNDAY) {
				break;
			}
			loday = loday.minusDays(1);

		}
		DayOfWeek prevdayweek = loday.getDayOfWeek();
		int weeknum2 = calw.weekcal(prevdayweek);
		lastday = lastday + weeknum2;
		LocalDate firstday = loday;
		

		for (int i = 1; i <= 7; i++) {
			week.add(loday);
			loday = loday.plusDays(1);
		}
		matrix.add(week);
		week = new ArrayList<>();

		int numloday = loday.getDayOfMonth() + 1;

		for (int i = numloday; i <= lastday; i++) {

			week.add(loday);

			DayOfWeek weekofloday = loday.getDayOfWeek();
			weekofloday = loday.getDayOfWeek();
			if (weekofloday == DayOfWeek.SATURDAY) {
				matrix.add(week);
				week = new ArrayList<>();

			}
			loday = loday.plusDays(1);
		}

		//LocalDate firstday = LocalDate.of(loday.getYear(), loday.getMonthValue(), 1);
		while (true) {
			DayOfWeek lodayweek = firstday.getDayOfWeek();

			if (lodayweek == DayOfWeek.SUNDAY) {
				break;
			}
			firstday = firstday.minusDays(1);

		}
		LocalDate nextlastmonth = loday.plusMonths(1);
		LocalDate nextlastday = LocalDate.of(nextlastmonth.getYear(), nextlastmonth.getMonthValue(), 1);
		DayOfWeek nextlastdayweek = nextlastday.getDayOfWeek();
		int numnextweek2 = nextlastdayweek.getValue();

		int nextdayofnum = 7 - numnextweek2;

		for (int i = 1; i < nextdayofnum; i++) {

			nextlastday = nextlastday.plusDays(1);

		}

		List<Tasks> Tlist = null;
		if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"))) {
			Tlist = taskrepo.findByDateBetweenAdmin(firstday, loday);
		} else {
			Tlist = taskrepo.findByDateBetween(firstday, loday, user.getName());
		}
		LinkedMultiValueMap<LocalDate, Tasks> DailyTaskList = new LinkedMultiValueMap<LocalDate, Tasks>();
		int tasknum = Tlist.size();
		for (int i = 0; i < tasknum; i++) {
			DailyTaskList.add(Tlist.get(i).getDate(), Tlist.get(i));
		}

		

		model.addAttribute("month", dayofMonth);
		model.addAttribute("matrix", matrix);
		model.addAttribute("tasks", DailyTaskList);
		

		return "/main";

	}
}
