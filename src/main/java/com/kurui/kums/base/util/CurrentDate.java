package com.kurui.kums.base.util;

import java.util.Calendar;
import java.util.Date;

public class CurrentDate {

	Calendar calendar;

	public CurrentDate() {
		calendar = null;
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
	}

	public int getYear() {
		return calendar.get(1);
	}

	public int getMonthInt() {
		return calendar.get(2) + 1;
	}

	public int getDay() {
		return calendar.get(5);
	}

	public int getWeek() {
		return calendar.get(7) - 1;
	}

	public int getWeek(int year, int month, int day) {
		calendar.set(year, month - 1, day);
		return getWeek();
	}

	public int getDayOfYear() {
		return calendar.get(6);
	}

	public int getDayOfMonth(int intMonth) {
		int daysInMonths[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		return daysInMonths[intMonth - 1];
	}

	public void setDate(int year, int month, int day) {
		calendar.set(year, month - 1, day);
	}

	public int getHour() {
		return calendar.get(11);
	}

	public int getMinute() {
		return calendar.get(12);
	}

	public int getSecond() {
		return calendar.get(13);
	}

	public String getDate() {
		return (new StringBuilder(String.valueOf(getYear()))).append("-")
				.append(getMonthInt()).append("-").append(getDay()).toString();
	}

	public String getSixDate() {
		return (new StringBuilder(String.valueOf(getYear()))).append(
				getTwoBitInt(getMonthInt())).append(getTwoBitInt(getDay()))
				.toString();
	}

	public String getTenDate() {
		return (new StringBuilder(String.valueOf(getYear()))).append("-")
				.append(getTwoBitInt(getMonthInt())).append("-").append(
						getTwoBitInt(getDay())).toString();
	}

	private String getTwoBitInt(int intStr) {
		if (intStr < 10)
			return (new StringBuilder("0")).append(intStr).toString();
		else
			return (new StringBuilder()).append(intStr).toString();
	}

	public String getTime() {
		String tempHour = "";
		String tempMin = "";
		String tempSecond = "";
		if (getHour() < 10)
			tempHour = (new StringBuilder("0")).append(getHour()).toString();
		else
			tempHour = (new StringBuilder(String.valueOf(getHour())))
					.toString();
		if (getMinute() < 10)
			tempMin = (new StringBuilder("0")).append(getMinute()).toString();
		else
			tempMin = (new StringBuilder(String.valueOf(getMinute())))
					.toString();
		if (getSecond() < 10)
			tempSecond = (new StringBuilder("0")).append(getSecond())
					.toString();
		else
			tempSecond = (new StringBuilder(String.valueOf(getSecond())))
					.toString();
		return (new StringBuilder(String.valueOf(getHour()))).append(":")
				.append(tempMin).append(":").append(tempSecond).toString();
	}

	public boolean ifBehindYear(int year) {
		return year < getYear();
	}

	public boolean ifBehindYearMonth(int year, int month) {
		if (year < getYear())
			return true;
		return year == getYear() && month < getMonthInt();
	}

	public boolean ifBehindYearMonthDay(int year, int month, int day) {
		if (year < getYear())
			return true;
		if (year == getYear() && month < getMonthInt())
			return true;
		return year == getYear() && month == getMonthInt() && day < getDay();
	}

	public int leftDaysInMonth() {
		int days = calendar.get(5);
		int m = calendar.get(2);
		int maxDay = calendar.getActualMaximum(5);
		return maxDay;
	}

	public static void main(String args[]) {
		CurrentDate cr = new CurrentDate();
		System.out.println((new StringBuilder("date=")).append(cr.getDate())
				.toString());
		cr.setDate(2002, 4, 21);
		System.out.println((new StringBuilder("year =")).append(cr.getYear())
				.toString());
		System.out.println((new StringBuilder("month =")).append(
				cr.getMonthInt()).toString());
		System.out.println((new StringBuilder("day=")).append(cr.getDay())
				.toString());
		System.out.println((new StringBuilder("week=")).append(cr.getWeek())
				.toString());
		System.out.println((new StringBuilder(String.valueOf(cr.getDate())))
				.append(" ").append(cr.getHour()).append(":").append(
						cr.getMinute()).append(":").append(cr.getSecond())
				.toString());
		System.out.println((new StringBuilder("week=")).append(
				cr.getWeek(2001, 2, 5)).toString());
		System.out.println((new StringBuilder("days=")).append(
				cr.leftDaysInMonth()).toString());
	}
}
