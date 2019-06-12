package com.github.surpassm.tool.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author minchao
 * @Version V1.0
 * @Description:时间换算工具类
 * @Company: www.cqprosper.com
 * @date 2017-09-06 23:26
 */
public class DateToolsUtil {
	public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm";
	public final static String DATE_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_CHINA_DEFAULT = "yyyy年MM月dd日 HH:mm";
	// 引入calendar类
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat dateFChinaDefault = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

	/**
	 * 获取月份起始日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormatMonth.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取月份最后日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormatMonth.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getYear(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取日期月份
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获取日期号
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 把日期格式化为指定格式并返回字符串
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 把日期格式化为指定格式并返回字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatHz(Date date) {
		return dateFChinaDefault.format(date);
	}

	/**
	 * 把日期格式化为指定格式并返回字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatMonth(Date date) {
		return dateFormatMonth.format(date);
	}


	/**
	 * 获取指定日期前后num天的日期
	 *
	 * @param date
	 * @param num  正数 多少天之后的日期  负数 多少天之后的日期
	 * @return
	 * @author yangwk
	 * @time 2017年9月14日 上午11:13:18
	 */
	public static String getDay(String date, int num) {
		return getDay(date, num, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 获取指定时间前num天的日期
	 *
	 * @param date 指定时间
	 * @param num  是定天数
	 * @return 日期
	 */
	public static Date getOldDay(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - num);
		return calendar.getTime();
	}

	/**
	 * 获取指定时间后num天的日期
	 *
	 * @param date 指定时间
	 * @param num  是定天数
	 * @return 日期
	 */
	public static Date getNowDay(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + num);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期前后num天的日期
	 *
	 * @param date
	 * @param num    正数 多少天之后的日期  负数 多少天之后的日期
	 * @param format 日期格式
	 * @return
	 * @author yangwk
	 * @time 2017年9月14日 上午11:13:18
	 */
	public static String getDay(String date, int num, String format) {
		long t = parseStringToLong(date);
		return getDay(t, num, DATE_FORMAT_DEFAULT);
	}


	/**
	 * 获取指定日期前后num天的日期
	 *
	 * @param date
	 * @param num  正数 多少天之后的日期  负数 多少天之后的日期
	 * @return
	 * @author yangwk
	 * @time 2017年9月14日 上午11:13:18
	 */
	public static String getDay(long date, int num) {
		return getDay(date, num, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 获取指定日期前后num天的日期
	 *
	 * @param date
	 * @param num    正数 多少天之后的日期  负数 多少天之后的日期
	 * @param format 日期格式
	 * @return
	 * @author yangwk
	 * @time 2017年9月14日 上午11:13:18
	 */
	public static String getDay(long date, int num, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + num);
		return longToString(calendar.getTimeInMillis(), format);
	}


	/**
	 * 将毫秒时间转换为yyyy-MM-dd格式的时间
	 *
	 * @param time 毫秒数
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午5:56:40
	 */
	public static String longToString(long time) {
		return longToString(time, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 将毫秒时间转换为指定格式的时间
	 *
	 * @param time   毫秒数
	 * @param format 日期格式
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午5:56:40
	 */
	public static String longToString(long time, String format) {
		if (StringUtils.isBlank(format)) {
			format = DATE_FORMAT_DEFAULT;
		}
		DateTime dTime = new DateTime(time);
		return dTime.toString(format);
	}

	/**
	 * 获取今天开始的时间
	 *
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午5:58:18
	 */
	public static Timestamp getTodayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定日期开始的当日开始时间
	 *
	 * @param date
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午5:58:33
	 */
	public static long getDayStartTime(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(parseStringToLong(date));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取指定日期结束时间
	 *
	 * @param date
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午5:58:58
	 */
	public static long getDayEndTime(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(parseStringToLong(date));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTimeInMillis();
	}

	/**
	 * 获得当前月份
	 */
	public static String getCurrentTime(){
		return getCurrentTime("yyyy-MM");
	}

	/**
	 * 获得当前时间
	 *
	 * @param format 日期格式
	 * @return
	 */
	public static String getCurrentTime(String format){
		DateTime dTime = new DateTime();
		return dTime.toString(format);
	}


	/**
	 * 获取月份起始日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMinMonthDateNow(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormatMonth.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取月份最后日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMaxMonthDateNow(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormatMonth.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}


	/**
	 * 将字符串类型的日期转换为毫秒数
	 *
	 * @param dateStr
	 * @return
	 * @author yangwenkui
	 * @time 2017年10月6日 下午6:00:27
	 */
	public static long parseStringToLong(String dateStr) {
		dateStr = dateStr.trim();
		if (dateStr.length() == 19 || dateStr.length() == 23) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(11, 13)),
						Integer.parseInt(dateStr.substring(14, 16)),
						Integer.parseInt(dateStr.substring(17, 19)));
				cal.set(Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}

		} else if (dateStr.length() == 16) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(11, 13)),
						Integer.parseInt(dateStr.substring(14, 16)));
				cal.set(Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}

		} else if (dateStr.length() == 14) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(4, 6)) - 1,
						Integer.parseInt(dateStr.substring(6, 8)),
						Integer.parseInt(dateStr.substring(8, 10)),
						Integer.parseInt(dateStr.substring(10, 12)),
						Integer.parseInt(dateStr.substring(12, 14)));
				cal.set(Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else if (dateStr.length() == 10 || dateStr.length() == 11) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(5, 7)) - 1,
						Integer.parseInt(dateStr.substring(8, 10)), 0, 0, 0);
				cal.set(Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else if (dateStr.length() == 8) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(dateStr.substring(0, 4)),
						Integer.parseInt(dateStr.substring(4, 6)) - 1,
						Integer.parseInt(dateStr.substring(6, 8)), 0, 0, 0);
				cal.set(Calendar.MILLISECOND, 0);
				return (cal.getTime().getTime());
			} catch (Exception e) {
				return 0;
			}
		} else {
			try {
				return Long.parseLong(dateStr);
			} catch (Exception e) {
				return 0;
			}

		}
	}

	/**
	 * 判断是否超过24小时
	 *
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return true 可用   false 不可用
	 */
	public static boolean judgeHour(Date start, Date end) {
		long cha = end.getTime() - start.getTime();
		double result = cha * 1.0 / (1000 * 60 * 60);
		return result <= 24;
	}
}
