package com.xxd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {


	public static final String Y_M_D = "yyyy-MM-dd";

	public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";

	public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";

	public static final String YMD = "yyyyMMdd";

	public static final String YMDHM = "yyyyMMddHHmm";

	public static final String YMDHMS = "yyyyMMddHHmmss";

	public static final String ymd = "yyyy/MM/dd";

	public static final String ymd_HM = "yyyy/MM/dd HH:mm";

	public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 智能转换日期
	 *
	 * @param date
	 * @return
	 */
	public static String smartFormat(Date date)
	{
		String dateStr = null;
		if (date == null){
			dateStr = "";
		}
		else{
			try{
				dateStr = formatDate(date, Y_M_D_HMS);
				// 时分秒
				if (dateStr.endsWith(" 00:00:00")){
					dateStr = dateStr.substring(0, 10);
				}
				// 时分
				else if (dateStr.endsWith("00:00")){
					dateStr = dateStr.substring(0, 16);
				}
				// 秒
				else if (dateStr.endsWith(":00")){
					dateStr = dateStr.substring(0, 16);
				}
			}
			catch (Exception ex){
				throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
			}
		}
		return dateStr;
	}

	/**
	 * 智能转换日期
	 *
	 * @param text
	 * @return
	 */
	public static Date smartFormat(String text)
	{
		Date date = null;
		try{
			if (text == null || text.length() == 0){
				date = null;
			}
			else if (text.length() == 10){
				date = formatStringToDate(text, Y_M_D);
			}
			else if (text.length() == 13){
				date = new Date(Long.parseLong(text));
			}
			else if (text.length() == 16){
				date = formatStringToDate(text, Y_M_D_HM);
			}
			else if (text.length() == 19){
				date = formatStringToDate(text, Y_M_D_HMS);
			}
			else{
				throw new IllegalArgumentException("日期长度不符合要求!");
			}
		}
		catch (Exception e){
			throw new IllegalArgumentException("日期转换失败!");
		}
		return date;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getNow(String format) throws Exception {
		return formatDate(new Date(), format);
	}

	/**
	 * 格式化日期格式
	 *
	 * @param argDate
	 * @param argFormat
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(Date argDate, String argFormat) throws Exception {
		if (argDate == null){
			throw new Exception("参数[日期]不能为空!");
		}
		if (argFormat==null||argFormat==""){
			argFormat = Y_M_D;
		}
		SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
		return sdfFrom.format(argDate).toString();
	}

	/**
	 * 把字符串格式化成日期
	 *
	 * @param argDateStr
	 * @param argFormat
	 * @return
	 */
	public static Date formatStringToDate(String argDateStr, String argFormat) throws Exception {
		if (argDateStr == null || argDateStr.trim().length() < 1){
			throw new Exception("参数[日期]不能为空!");
		}
		SimpleDateFormat sdfFormat = new SimpleDateFormat(argFormat);
		try{
			return sdfFormat.parse(argDateStr);
		}
		catch (ParseException e){
			throw new Exception(e);
		}
	}

	/**
	 * 比较两个日期相差天数
	 * @param startDate
	 * @param endDate
     * @return
     */
	public static int timeGap(Date startDate, Date endDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(endDate);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	/**
	 * 两个时间相差的几天数组返回
	 * @param startDate
	 * @param endDate
     * @return
     */
	public static String[] twoDaysApart(Date startDate, Date endDate) throws Exception {
		int days = DateUtil.timeGap(startDate, endDate);
		Calendar calendar = new GregorianCalendar();
		if (days == 0) {
			return new String[]{DateUtil.formatDate(startDate, DateUtil.Y_M_D)};
		} else if (days > 0) {
			String[] dayArray = new String[days+1];
			for (int i = 0; i < days+1; i++) {
				calendar.setTime(startDate);
				calendar.add(Calendar.DATE, i);
				dayArray[i] = DateUtil.formatDate(calendar.getTime(), DateUtil.Y_M_D);
			}
			return dayArray;
		}
		return null;
	}

}
