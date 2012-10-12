package com.kurui.kums.base.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.kurui.kums.base.Constant;

public class DateUtil {

	public static void main(String arg[]) {
		try {
			String[] days = null;

			// days = getDaysOfMonth(2011, 9);

			// System.out.println("yesterdayDate:" +getYesterDay(2));//前N天
			// System.out.println("tommorrowdayDate:"+getTomorrowDay(3));//未来N天

			// days = getLastDays(30);
			// days = getDaysOfStartEnd("2011-07-01", "2011-07-03");
			// days = getDaysOfStartEnd("2011-09-28", "2011-10-03");

			// PrintDataUtil.printArrayln(days);

			// System.out.println(getDateString("2011-11-05 23:59:59",
			// "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
//			getThisWeekDays();
//			getLastWeekDays5();
//			getLastWeekDays7();
			
			System.out.println(getTimestamp("", "yyyy-MM-dd"));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getDateString(String dateStr, String oldPattern,
			String targetPattern) {
		Timestamp date = getTimestamp(dateStr, oldPattern);
		return getDateString(date, targetPattern);
	}

	public static String getDateString(String pattern) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(Long.valueOf(date.getTime()));
	}

	public static Timestamp getTimestamp(String dateStr, String pattern) {
		SimpleDateFormat dateFormat;
		Date date;
		dateFormat = new SimpleDateFormat(pattern);
		date = null;
		try {
			if(dateStr!=null&&"".equals(dateStr)==false){
				date = dateFormat.parse(dateStr);
				return new Timestamp(date.getTime());
			}else{
				return null;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Timestamp((new Date()).getTime());
	}

	public static Date getDate(String dateStr, String pattern) {
		SimpleDateFormat dateFormat;
		Date date;
		dateFormat = new SimpleDateFormat(pattern);
		date = null;
		try {
			date = dateFormat.parse(dateStr);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}

	public static String TransMonthToInt(String str) {
		String eMonth[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
				"AUG", "SEP", "OCT", "NOV", "DEC" };
		String temp = "";
		for (int i = 0; i < eMonth.length; i++) {
			if (!str.equals(eMonth[i]))
				continue;
			temp = i >= 10 ? (new StringBuilder()).append(i).toString()
					: (new StringBuilder("0")).append(i).toString();
			break;
		}

		return temp;
	}

	public static String[] getDaysOfMonth(int year, int month) {
		MonthUtil monthUtil = new MonthUtil(year, month);

		// monthUtil.printMonthDays();
		String[] days = monthUtil.getDaysOfMonth();
		return days;
	}

	// 获取两日之间的的所有日期
	public static String[] getDaysOfStartEnd(String startDate, String endDate) {
		BetweenDayUtil dayUtil = new BetweenDayUtil(startDate, endDate);
		String[] days = dayUtil.getDaysOfStartEnd();

		return days;
	}

	// 获取前N天
	public static String getYesterDay(Calendar calendar, int preDays) {
		calendar.add(Calendar.DATE, -preDays); // 得到前N天
		return getDateStrByCalendar(calendar);
	}

	// 获取前N天
	public static String getYesterDay(int preDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -preDays); // 得到前N天
		return getDateStrByCalendar(calendar);
	}

	public static String getDateStrByCalendar(Calendar calendar) {
		String yestedayDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(calendar.getTime());
		return yestedayDate;
	}

	// 上周的今天
	public static String getLastWeekToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		return getDateStrByCalendar(calendar);
	}

	// 获取最近N天的全部日期
	public static String[] getLastDays(int lastDays) {
		String[] lastDaysArray = new String[lastDays];
		for (int i = 0; i < lastDays; i++) {
			String lastdayDate = getYesterDay(i + 1);
			lastDaysArray[i] = lastdayDate;
		}
		return lastDaysArray;
	}

	// 获取未来N天
	public static String getTomorrowDay(Calendar calendar, int tomorrowDay) {
		calendar.add(Calendar.DATE, tomorrowDay); // 得到前N天
		return getDateStrByCalendar(calendar);
	}

	// 获取未来N天
	public static String getTomorrowDay(int tomorrowDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, tomorrowDay); // 得到前N天
		return getDateStrByCalendar(calendar);
	}

	// 本周
	public static String[] getThisWeekDays() {
		String[] days = null;
		GregorianCalendar calendar = new GregorianCalendar();
		String now = new java.sql.Date(new Date().getTime()).toString();
		int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) - 2;
		calendar.add(GregorianCalendar.DATE, -minus);
		String begin = getDateStrByCalendar(calendar);
		;
		System.out.println("本周 begin: " + begin);
		System.out.println(" end: " + now);

		days = getDaysOfStartEnd(begin, now);

		return days;
	}

	// 上周
	public static String[] getLastWeekDays5() {
		String[] days = null;
		GregorianCalendar calendar = new GregorianCalendar();

		int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;
		calendar.add(GregorianCalendar.DATE, -minus);
		String end = getDateStrByCalendar(calendar);

		calendar.add(GregorianCalendar.DATE, -4);
		String begin = getDateStrByCalendar(calendar);

		System.out.println("上周5天制 begin: " + begin);
		System.out.println(" end: " + end);
		days = getDaysOfStartEnd(begin, end);
		return days;
	}

	// 上周
	public static String[] getLastWeekDays7() {
		String[] days = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, -1);

		String end = getDateStrByCalendar(calendar);

		calendar.add(Calendar.DATE, -6);// begin

		String begin = getDateStrByCalendar(calendar);

		System.out.println("上周7天制 begin: " + begin);
		System.out.println(" end: " + end);

		days = getDaysOfStartEnd(begin, end);
		return days;
	}

	public static class BetweenDayUtil {
		private int allday;
		private Calendar calendar;
		private String startDate;
		private String endDate;

		public BetweenDayUtil(String startDate1, String endDate1) {
			startDate = startDate1;
			endDate = endDate1;

			if ("".equals(startDate1) == false && "".equals(endDate1) == false) {
				setCalendar(startDate1);
			}
		}

		public void setCalendar(String endDate1) {
			Date date = DateUtil.getDate(endDate1, "yyyy-MM-dd");
			int year = Constant.toLong(DateUtil.getDateString(date, "yyyy"))
					.intValue();
			int month = Constant.toLong(DateUtil.getDateString(date, "MM"))
					.intValue();
			int d = Constant.toLong(DateUtil.getDateString(date, "dd"))
					.intValue();

			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DATE, d);

		}

		public String[] getDaysOfStartEnd() {
			String[] days = new String[100];// 如何动态创建

			int dayIndex = 0;

			days[dayIndex] = startDate;
			dayIndex++;

			for (int i = 0; i < days.length; i++) {
				String tomday = getTomorrowDay(calendar, 1);
				if (tomday != null && "".equals(tomday) == false) {
					if (endDate.equals(tomday) == false) {
						if (dayIndex > 99) {
							break;
						}
						days[dayIndex] = tomday;
						dayIndex++;
					} else {
						days[dayIndex] = tomday;
						break;
					}
				}
			}

			allday = dayIndex + 1;

			days = StringUtil.removeSpilthSpace(days, allday);

			return days;
		}
	}

	public static class MonthUtil {
		private int[] monthsInYear = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		private int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31 };
		public int allday;
		private Calendar calendar;

		private int year;
		private int month;

		public MonthUtil(int year, int month) {
			setCalendar(year, month);
		}

		public void setCalendar(int year1, int month1) {
			year = year1;
			month = month1;
			// System.out.println("------------" + year + "年" + month
			// + "月份------------\n");
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DATE, 1);
			if ((year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
					&& month == 2) {
				daysInMonths[1]++;
			}
			allday = daysInMonths[month - 1];
			// System.out.println("allday:"+allday);
		}

		public void printMonthDays() {
			System.out.println("SUN\tMON\tTUR\tWED\tTHU\tFRI\tSAT");
			int first = calendar.get(Calendar.DAY_OF_WEEK);

			for (int i = 1; i < first; i++) {
				System.out.print("\t");
			}

			for (int i = 1; i <= allday; i++) {
				System.out.print(i + "\t");
				if (first++ % 7 == 0)
					System.out.println();
			}
		}

		// 获取某月的所有日期2011-10-11
		public String[] getDaysOfMonth() {
			String yearMonth = year + "-" + month;
			String[] days = new String[allday];
			for (int i = 1; i <= allday; i++) {
				days[i - 1] = yearMonth + "-" + i;
			}
			return days;
		}

		// 获取某月的所有日期{1,2,3....30,31}
		public int[] getDaysNoOfMonth() {
			int[] days = new int[allday];
			for (int i = 1; i <= allday; i++) {
				days[i - 1] = i;
			}
			return days;
		}

		public static void main(String arg[]) {
			try {
				MonthUtil monthUtil = new MonthUtil(2011, 9);
				monthUtil.printMonthDays();
				String[] days = monthUtil.getDaysOfMonth();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
