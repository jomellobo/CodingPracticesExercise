package com.orangeandbronze.coding_practices.leave.domain;

import static java.time.Month.*;
import static java.time.MonthDay.*;

import java.time.*;

public enum Holiday {
	NEW_YEAR(of(JANUARY, 1)), ARAW_NG_KAGITINGAN(of(APRIL, 9)), LABOR_DAY(
			of(MAY, 1)), INDEPENDENCE_DAY(of(JUNE, 1)), NATIONAL_HEROES_DAY(
			of(AUGUST, 30)), BONIFACIO_DAY(of(NOVEMBER, 30)), CHRISTMAS_EVE(
			of(DECEMBER, 24)), CHRISTMAS(of(DECEMBER, 25)), RIZAL_DAY(of(
			DECEMBER, 30)), NEW_YEARS_EVE(of(DECEMBER, 31));

	private final MonthDay monthDay;

	private Holiday(MonthDay monthDay) {
		this.monthDay = monthDay;
	}
	
	public MonthDay getMonthDay() {
		return monthDay;
	}
	/*
	 * TODO We're pulling out values from the Holiday enum in order to
	 * check if the currentDate is a holiday. Can we push this operation
	 * into the object that has the information?
	 */
	// skip holidays
	public static boolean isHoliday(LocalDate currentDate) {
		for (Holiday holiday : Holiday.values()) {
			if (MonthDay.from(currentDate).equals(holiday.getMonthDay())) {
				return true;
			}
		}
		return false;
	}
}
