package com.github.surpassm.tool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class DateUtil {

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
	public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATETIME_FORMATTER_MINUTE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	public static final DateTimeFormatter LONG_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

	/**
	 * 返回当前的日期
	 */
	public static LocalDate getCurrentLocalDate() {
		return LocalDate.now();
	}
	/**
	 * 返回当前时间
	 */
	public static LocalTime getCurrentLocalTime() {
		return LocalTime.now();
	}
	/**
	 * 返回当前日期时间
	 */
	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now();
	}
	/**
	 * 日期相隔秒
	 */
	public static long periodHours(LocalDateTime startDateTime,LocalDateTime endDateTime){
		return Duration.between(startDateTime, endDateTime).get(ChronoUnit.SECONDS);
	}
	/**
	 * 日期相隔天数
	 */
	public static long periodDays(LocalDate startDate, LocalDate endDate) {
		return startDate.until(endDate, ChronoUnit.DAYS);
	}

	/**
	 * 日期相隔周数
	 */
	public static long periodWeeks(LocalDate startDate, LocalDate endDate) {
		return startDate.until(endDate, ChronoUnit.WEEKS);
	}

	/**
	 * 日期相隔月数
	 */
	public static long periodMonths(LocalDate startDate, LocalDate endDate) {
		return startDate.until(endDate, ChronoUnit.MONTHS);
	}

	/**
	 * 日期相隔年数
	 */
	public static long periodYears(LocalDate startDate, LocalDate endDate) {
		return startDate.until(endDate, ChronoUnit.YEARS);
	}

	/**
	 * 是否当天
	 */
	public static boolean isToday(LocalDate date) {
		return getCurrentLocalDate().equals(date);
	}
	/**
	 * 获取当前毫秒数
	 */
	public static Long toEpochMilli(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 判断是否为闰年
	 */
	public static boolean isLeapYear(LocalDate localDate){
		return localDate.isLeapYear();
	}

	/**
	 * 将时间yyyy-MM-dd转换为时间戳
	 */
	public static Long dateToStamp(String s){
		return LocalDate.parse(s,DATE_FORMATTER).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	/**
	 * 将时间yyyy-MM-dd HH:mm转换为时间戳
	 */
	public static Long dateToStampEndM(String s){
		return LocalDateTime.parse(s,DATETIME_FORMATTER_MINUTE).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	/**
	 * 将时间戳转化为时间
	 */
	public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	/**
	 * 将某时间字符串转为自定义时间格式的LocalDateTime
	 */
	public static LocalDateTime parseStringToDateTime(String time, String format) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(time, df);
	}

	/**
	 * 将LocalDateTime转为自定义的时间格式的字符串
	 */

	public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
	}
	/**
	 * 计算两个日期之间相差
	 */
	public static Duration getLocalDateTimeBetween(LocalDateTime start,LocalDateTime end) {
		return Duration.between(start,end);
	}
	/**
	 * 计算两个日期之间相差的天数
	 */
	public static Long daysBetween(LocalDateTime start,LocalDateTime end){
		Duration duration = getLocalDateTimeBetween(start, end);
		return duration.toDays();
	}
	/**
	 * 获取当天开始时间
	 */
	public static LocalDateTime getStartTime() {
		return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
	}
	/**
	 * 获取当天结束时间
	 */
	public static LocalDateTime getEndTime() {
		return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
	}
	/**
	 * 获取指定时间开始时间
	 */
	public static LocalDateTime getStartTime(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MIN);
	}
	/**
	 * 获取指定时间结束时间
	 */
	public static LocalDateTime getEndTime(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MAX);
	}
	/**
	 * 字符串转时间yyyy-MM-dd
	 */
	public static LocalDate toLocalDate(String s){
		return LocalDate.parse(s,DATE_FORMATTER);
	}
	/**
	 * 字符串转时间yyyy-MM-dd HH:mm:ss
	 */
	public static LocalDateTime toLocalDateTime(String s){
		return LocalDateTime.parse(s,DATETIME_FORMATTER);
	}

	/*------------------------------------------------------*/



	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static long hourBetween(Long smdate, Long bdate) {
		long diff = smdate - bdate;
		return diff % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 日期加一个月
	 */
	public static Date addOneMonth(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
		date = calendar.getTime();   //这个时间就是日期往后推一天的结果
		return date;
	}

	/**
	 * 获取一天开始时间
	 */
	public static Date getStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 获取一天结束时间
	 *
	 * @return
	 */
	public static Date getEndTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	/**
	 * 获取昨天天开始时间
	 */
	public static Date getYesterdayStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.add(Calendar.DATE, -1);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 获取昨天结束时间
	 *
	 * @return
	 */
	public static Date getYesterdayEndTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.DATE, -1);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	/**
	 * 获取昨天天开始时间
	 */
	public static Date getFiveStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.add(Calendar.DATE, -5);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	public static Date string2date(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = sdf.parse(dateStr);
		return dateTime;
	}

	public static String daysBetweenString(Date date1, Date date2) throws Exception {
		int i = DateUtil.daysBetween(date1, date2);
		if (date1.getTime() - date2.getTime() > 0) {
			return "超时";
		} else {
			return i + "天";
		}

	}

	/**
	 * 获取当月第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar monthStart = Calendar.getInstance();
		monthStart.setTime(date);
		monthStart.add(Calendar.MONTH, 0);
		monthStart.set(Calendar.DATE, 1);
		monthStart.set(Calendar.HOUR_OF_DAY, 0);
		monthStart.set(Calendar.MINUTE, 0);
		monthStart.set(Calendar.SECOND, 0);
		monthStart.set(Calendar.MILLISECOND, 0);
		return monthStart.getTime();
	}

	/**
	 * 获取下月第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextMonthStart(Date date) {
		Calendar nextMonthStart = Calendar.getInstance();
		nextMonthStart.setTime(date);
		nextMonthStart.set(Calendar.MONTH, nextMonthStart.get(Calendar.MONTH) + 1);
		return getMonthStart(nextMonthStart.getTime());
	}

	public static Date getSystemDate() throws Exception {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String dateStr = dateFormat.format(date);
		Date time = dateFormat.parse(dateStr);
		return time;
	}

	//获取三个月前第一天
	public static Date getThreeMonth(Date date) {
		Calendar monthStart = Calendar.getInstance();
		monthStart.setTime(date);
		monthStart.set(Calendar.MONTH, monthStart.get(Calendar.MONTH) - 3);
		monthStart.set(Calendar.DATE, 1);
		monthStart.set(Calendar.HOUR_OF_DAY, 0);
		monthStart.set(Calendar.MINUTE, 0);
		monthStart.set(Calendar.SECOND, 0);
		monthStart.set(Calendar.MILLISECOND, 0);

		return monthStart.getTime();
	}

	/**
	 * 获取day数后的日期
	 *
	 * @param day
	 * @return
	 */
	public static Date getDayNumber(Date date,Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,day);
		return calendar.getTime();
	}
}
