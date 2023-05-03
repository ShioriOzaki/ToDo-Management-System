package com.dmm.task.data.repository;

import java.time.DayOfWeek;

public class CalcWeek {
	public int weekcal(DayOfWeek week) {
		int weeknum = 0;
		switch (week) {
		case SUNDAY:
			weeknum = 1;
			break;
		case MONDAY:
			weeknum = 2;
			break;
		case TUESDAY:
			weeknum = 3;
			break;
		case WEDNESDAY:
			weeknum = 4;
			break;
		case THURSDAY:
			weeknum = 5;
			break;
		case FRIDAY:
			weeknum = 6;
			break;
		case SATURDAY:
			weeknum = 7;
			break;
		}

		return weeknum;

	}

}
