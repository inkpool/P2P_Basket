package com.p2pnote.utility;

import java.util.Date;
import java.util.GregorianCalendar;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.database.Cursor;
import android.util.Log;

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.model.UserChannelCurrAmount;

public class Function {
	private final int DATE_MOTH_DAY = 1;
	private final int DATE_DAY = 2;
	private static Calendar calendar = Calendar.getInstance();

	// 计算利息，待强化
	public static Double getInterest(Double amount,Double interest_min, String date1, String date2, String repayment_type) {
		int days=nDaysBetweenTwoDate(date1, date2);
		double result=0;
		if (repayment_type.equals("按月只还息"))
		{
			result=amount*interest_min/100*days/365;
		}
		//即最常见的等额本息
		else if (repayment_type.equals("按月还本息")) {
			try {
				result=getAverageMonthPay(amount,date1,date2,interest_min)*getMonthSpace(date1, date2)-amount;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		else if (repayment_type.equals("到期还本息")) {
			result=amount*interest_min/100*days/365;
		}
		
		//四舍五入
		double tmp=result;
		BigDecimal bg = new BigDecimal(tmp);
		result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
		
	// 计算等额本息的月还款额，总利息=月还款额*月份-本金
	public static Double getAverageMonthPay(Double a, String date1, String date2,
			Double interest_rate) {
		Double result = null;
		Double i = interest_rate /100/ 12;  //月利率
		int n;
		try {
			n = getMonthSpace(date1, date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// 月均还款本息计算，a×i×（1＋i）^n÷〔（1＋i）^n－1〕

		double tmp = a * i * Math.pow((1 + i), n) / (Math.pow((1 + i), n) - 1);
		BigDecimal bg = new BigDecimal(tmp);
		result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

	// 计算等额本息的剩余本金
	public static Double getRemainAmount(Double a, String date1, String date2,
			Double interest_rate, Date current_date) {
		Double result = a;
		Double i = interest_rate / 12;
		date2 = getCurrentDate();

		int n;
		try {
			n = getMonthSpace(date1, date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// 月均还款本息计算，a×i×（1＋i）^n÷〔（1＋i）^n－1〕
		Double pay = getAverageMonthPay(a, date1, date2, interest_rate);

		for (int cnt = 1; cnt <= n; cnt++) {
			result = result - (pay - a * i);
		}
		return result;
	}

	// ==================== date function
	// ===========================================//

	public static long getCurrentTime() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static long getFirstOfWeek(long time) {
		Date date1, date2;
		date1 = new Date(time);
		date2 = new Date(date1.getYear(), date1.getMonth(), date1.getDate()
				- (date1.getDay() + 6) % 7);
		return date2.getTime();
	}

	public static long getLastOfWeek(long time) {
		Date date1, date2;
		date1 = new Date(time);
		date2 = new Date(date1.getYear(), date1.getMonth(), date1.getDate()
				- (date1.getDay() + 6) % 7 + 6);
		return date2.getTime();
	}

	public static long getFirstOfMonth(long time) {
		Date date1, date2;
		date1 = new Date(time);
		date2 = new Date(date1.getYear(), date1.getMonth(), 1);
		return date2.getTime();
	}

	public static long getLastOfMonth(long time) {
		Date date1, date2;
		date1 = new Date(time);
		date2 = new Date(date1.getYear(), date1.getMonth() + 1, 0);
		return date2.getTime();
	}

	/**
	 * <pre>
	 * 返回指定时间的某天之后的时间(当前时刻).
	 * </pre>
	 * 
	 * @param _from
	 *            null表示当前
	 * @param _days
	 *            可以为负值
	 * @return
	 */
	public static Date getDateAfter(final Date _from, final int _days) {
		return getDateTimeAfter(_from, Calendar.DAY_OF_MONTH, _days);
	}

	public static Date getDateTimeAfter(final Date _from, final int _time_type,
			final int _count) {
		final Calendar c = Calendar.getInstance();
		if (_from != null) {
			c.setTime(_from);
		}
		c.add(_time_type, _count);
		return c.getTime();
	}

	public static String getCurrentDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date current_date = new Date(System.currentTimeMillis());
		return sDateFormat.format(current_date);
	}

	/**
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2)
			throws ParseException {

		int result = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

		return result == 0 ? 1 : Math.abs(result);

	}

	// 计算两个日期相隔的天数
	public static int nDaysBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}

		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	// 取今天
	public static String initDate() {
		int year = 0, month = 0, day = 0;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);

		return DateToStr(year, month, day); // 显示当前的年月日
	}

	// 取今天，用于日期对话框
	public static int[] getYearMonthDay() {
		int year = 0, month = 0, day = 0;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);
		int[] result = new int[3];
		result[0] = year;
		result[1] = month;
		result[2] = day;
		return result; // 显示当前的年月日
	}

	public static String DateToStr(int year, int month, int day) {
		String str_month = String.valueOf(month);
		String str_day = String.valueOf(day);
		if (month < 10)
			str_month = "0" + str_month;

		if (day < 10)
			str_day = "0" + str_day;

		String result = String.valueOf(year) + "-" + str_month + "-" + str_day; // 显示当前的年月日
		return result;
	}

	// 取昨天
	public String getDefaultDay() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.DATE, -1);
		str = format(lastDate.getTime(), DATE_DAY);
		return str;
	}

	// 取下周
	public static String getNextweek() {
		String str = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 7);

		String year1 = String.valueOf(calendar.get(Calendar.YEAR));
		String month1 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (Integer.parseInt(month1) < 10)
			month1 = "0" + month1;
		String day1 = String.valueOf(calendar.get(Calendar.DATE));
		if (Integer.parseInt(day1) < 10)
			day1 = "0" + day1;
		return year1 + "-" + month1 + "-" + day1;
	}

	// 取下月
	public static String getNextMonth() {
		String str = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);

		String year1 = String.valueOf(calendar.get(Calendar.YEAR));
		String month1 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (Integer.parseInt(month1) < 10)
			month1 = "0" + month1;
		String day1 = String.valueOf(calendar.get(Calendar.DATE));
		if (Integer.parseInt(day1) < 10)
			day1 = "0" + day1;
		return year1 + "-" + month1 + "-" + day1;
	}

	private static String format(Date date, int id) {
		String str = "";
		SimpleDateFormat ymd = null;
		switch (id) {
		default:
			ymd = new SimpleDateFormat("yyyy-MM-dd");
			break;
		}
		str = ymd.format(date);
		return str;
	}

	private static int getMondayPlus() {
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		String preMonday = format(monday, 0);
		String weekEnd = null;
		// weekEnd = format(monday, DATE_MOTH_DAY);
		return preMonday;
	}

	public static String getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
		Date monday = currentDate.getTime();

		String preMonday = format(monday, 0);
		return preMonday;
	}

	private String getP2pChannelName(Long id, MyDbHelper db) {
		String pName = null;
		// select NAME from TBL_BANK_TYPE
		Cursor cursor = db.rawQuery(
				"select SHORTNAME  from TBL_BANK_TYPE  where ID=?",
				new String[] { String.valueOf(id) });

		while (cursor.moveToNext()) {
			if (cursor.getString(0) != null) { //
				pName = cursor.getString(cursor.getColumnIndex("SHORTNAME"));

				Log.e("hzm", pName);
			}

		}
		cursor.close();

		return pName;
	}

	/*
	 * private int getRepayDay(int id){
	 * 
	 * db = MyDbHelper.getInstance(mContext); db.open(); Cursor cursor =
	 * db.rawQuery("select  *  from TBL_BANK_CARD_INFO where ID=? " ,new
	 * String[]{ String.valueOf(id)}); while (cursor.moveToNext()) {
	 * Log.e("hzm", "ge"+cursor.getCount());
	 * 
	 * cardNum=cursor.getString(cursor.getColumnIndex("CARD_NUM"));
	 * alarmId=cursor.getInt(cursor.getColumnIndex("ID")); //作为闹钟ID
	 * alarmType=cursor.getInt(cursor.getColumnIndex("REPAYMENT_TYPE")); //还款类型
	 * billDate=cursor.getInt(cursor.getColumnIndex("BILL_DATE")); //账单日
	 * repayDate=cursor.getInt(cursor.getColumnIndex("REPAYMENT_DATE")); //还款日
	 * 
	 * } cursor.close(); db.close();
	 * 
	 * Calendar c1 = Calendar.getInstance();
	 * c1.setTimeInMillis(System.currentTimeMillis());
	 * c1.set(Calendar.HOUR_OF_DAY, 9); //将时间设置为当日 9:00 c1.set(Calendar.MINUTE,
	 * 0);
	 * 
	 * BankRecordAlarm alarm=new BankRecordAlarm(mContext); Calendar
	 * c2=alarm.calculateAlarmDate(alarmType, billDate, repayDate);
	 * 
	 * return daysBetween(c1,c2); }
	 */
}
