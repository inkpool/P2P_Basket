package com.crowley.p2pnote.functions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.db.RecordModel;
import com.github.mikephil.charting.data.Entry;

public class ReturnList {
	
	private Context context;
	
	private Calendar cal;
	private int year;
	private int month;
	private int[] months={0,31,28,31,30,31,30,31,31,30,31,30,31};
	private int day;
	private int days;
	
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private Cursor allRecords;
	
	private final int DATE_MOTH_DAY = 1;
	private final int DATE_DAY = 2;
	private static Calendar calendar = Calendar.getInstance();
	
	public ReturnList(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		this.helper = new DBOpenHelper(context, "record.db");
		this.db = this.helper.getWritableDatabase();
		this.allRecords = this.helper.returALLRecords(this.db);
		
		this.cal=Calendar.getInstance();
		this.year = this.cal.get(Calendar.YEAR);
		this.month = this.cal.get(Calendar.MONTH)+1;
		this.day = this.cal.get(Calendar.DAY_OF_MONTH);
		this.days=this.year*365+this.month*this.months[this.month]+this.day;
	}	
	
	public String getTime(){
		String monthString;
		String dayString;
		if (month<10) {
			monthString="-0"+month;			
		}else{
			monthString="-"+month;
		}
		if (day<10) {
			dayString="-0"+day;
		}else{
			dayString="-"+day;
		}
		return year+monthString+dayString;
	}
	
	public String getBaseInfo01Number01(){
		return "20.95";
	}
	
	public String getBaseInfo01Number02(){
		return "1";
	}
	
	public String getBaseInfo02Number01(){
		return "8.5";
	}
	
	public String getBaseInfo02Number02(){
		return "1.2 %";
	}
	
	public String getBaseInfo03(){
		return "135600.58";
	}

	public int parseDay(String date){
		String[] time=date.split("-");
		int year=Integer.parseInt(time[0]);
		int month=Integer.parseInt(time[1]);
		int day=Integer.parseInt(time[2]);	
		return year*365+month*this.months[month]+day;		
	}
	
	//type为0表示到期时间，1表示金额，2表示收益率
	public List<Map<String, Object>> waterSort(int type,boolean des){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				temp.clear();
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				map.put("time", record.getTimeBegin()+" 至 "+record.getTimeEnd());
				int icon = DBOpenHelper.PLATFORM_ICONS[0];
				for(int i=0;i<9;i++){
					if((record.getPlatform()).equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
						icon=DBOpenHelper.PLATFORM_ICONS[i];						
					}
				}
				map.put("item_icon", icon);
				map.put("item_name", record.getPlatform()+"-"+record.getType());
				map.put("item_money", record.getMoney());
				if (record.getEarningMin()==0.0) {
					map.put("item_profit", (record.getEarningMax()*100)+"%");					
				}else{
					map.put("item_profit", (record.getEarningMin()*100)+"%~"+(record.getEarningMax()*100)+"%");
				}
				//如果为0，直接添加元素
				if(dataList.size()==0){
					dataList.add(map);
				}else{
					//是否已经添加了，如果没有添加过，则说明该元素应该直接插入到表尾，所以在最后的时候直接add即可
					boolean added=false;
					for(int i=0;i<dataList.size()&&!added;i++){
						boolean judge=false;
						switch (type) {
							case 0:{
								String mapString=(((String) map.get("time")).split(" "))[(((String) map.get("time")).split(" ")).length-1];
								String compareString=(((String) (dataList.get(i)).get("time")).split(" "))[(((String) (dataList.get(i)).get("time")).split(" ")).length-1];
								if(parseDay(mapString)>(parseDay(compareString))){
									judge=true;
								}							
								break;							
							}
							case 1:{							
								if(((Float) map.get("item_money"))>((Float) (dataList.get(i)).get("item_money"))){
									judge=true;
								}				
								break;							
							}
							case 2:{
								String now = (String) map.get("item_profit");
								String[] nowArray=now.split("~");
								Float nowRate = Float.parseFloat(nowArray[nowArray.length-1].split("%")[0]);
								String compare = (String) (dataList.get(i)).get("item_profit");
								String[] compareArray=compare.split("~");
								Float compareRate = Float.parseFloat(compareArray[compareArray.length-1].split("%")[0]);
								if(nowRate>compareRate){
									judge=true;
								}						
								break;							
							}
							case 3:{
								String mapString=(((String) map.get("time")).split(" "))[0];
								String compareString=(((String) (dataList.get(i)).get("time")).split(" "))[0];
								if(parseDay(mapString)>(parseDay(compareString))){
									judge=true;
								}							
								break;
							}
							default:
								break;
						}
						if (judge) {
							for(int j=0;j+i<dataList.size();j++){
								temp.add(dataList.get(j+i));
							}
							dataList.set(i, map);
							for(int k=0;k<temp.size();k++){
								if(i+1+k<dataList.size()){
									dataList.set(i+1+k,temp.get(k));
								}else{
									dataList.add(temp.get(k));
								}									
							}
							added=true;
						}
					}
					if (!added) {
						dataList.add(map);						
					}
				}				
			}
		}
		//如果不是降序，则反转dataList
		if (!des) {
			temp.clear();
			for(int i=0;i<dataList.size();i++){
				temp.add(dataList.get(i));
			}
			for(int i=0;i<dataList.size();i++){
				dataList.set(i, temp.get(dataList.size()-1-i));
			}					
		}
		return dataList;		
	}
	
	//type为0表示已经到期，1表示即将到期
	public List<Map<String, Object>> indexList(int type){
		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				Map<String, Object> map=new HashMap<String, Object>();
				RecordModel record=new RecordModel(allRecords);
				boolean judge=false;
				switch (type) {
					case 0:{
						int nowDays=year*365+month*30+day;
						if(parseDay(record.getTimeEnd())<=nowDays){
							judge=true;												
						}
						break;
					}
					case 1:{
						int daysLeft=parseDay(record.getTimeEnd())-this.days;
						if(daysLeft<100&&daysLeft>0){
							judge=true;							
						}
						break;
					}
					default:
						break;
				}
				if (judge) {
					map.put("time", record.getTimeBegin()+" 至 "+record.getTimeEnd());
					int icon = DBOpenHelper.PLATFORM_ICONS[0];
					for(int i=0;i<9;i++){
						if(record.getPlatform().equals(context.getResources().getString(DBOpenHelper.PLATFORM_NAMES[i]))){
							icon=DBOpenHelper.PLATFORM_ICONS[i];						
						}
					}
					map.put("item_icon", icon);
					map.put("item_name", record.getPlatform()+"-"+record.getType());
					map.put("item_money", record.getMoney());
					if (record.getEarningMin()==0.0) {
						map.put("item_profit", (record.getEarningMax()*100)+"%");					
					}else{
						map.put("item_profit", (record.getEarningMin()*100)+"%~"+(record.getEarningMax()*100)+"%");
					}
					dataList.add(map);
				}				
			}
		}
		return dataList;		
	}
	
	//type为0表示已经到期，1表示即将到期
	public int indexCount(int type){
		int count=0;
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				switch (type) {
				case 0:{
					if(parseDay(record.getTimeEnd())<=this.days){
						count++;						
					}
					break;
				}
				case 1:{
					int daysLeft=parseDay(record.getTimeEnd())-this.days;
					if(daysLeft<100&&daysLeft>0){
						count++;						
					}
					break;
				}
				default:
					break;
				}
				
			}
		}
		return count;		
	}

	//type为0表示平台在投金额分析，4为平台余额分析
	public ArrayList<String> analyzexVals(int type){
        ArrayList<String> xVals = new ArrayList<String>();
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				switch (type) {
					case 0:{
						int time=parseDay(record.getTimeEnd());
						if(time>this.days){
							if(xVals.size()==0){
								xVals.add(record.getPlatform());
							}else{
								int count=0;
								for(int i=0;i<xVals.size();i++){
									if(!(record.getPlatform()).equals(xVals.get(i))){
										count++;																		
									}
								}
								if(count==xVals.size()){
									xVals.add(record.getPlatform());
								}
							}
						}
						break;
					}
					case 4:{
						break;
					}
					default:
						break;
				}
				
			}
		}
		return xVals;
	}
		
	//type为0表示平台在投金额分析，1为收益率，2为期限结构，3为回款时间，4为平台余额分析
	public ArrayList<Entry> analyzeEntries(int type,ArrayList<String> xVals){
		ArrayList<Entry> entries1 = new ArrayList<Entry>();
		ArrayList<Float> counts = new ArrayList<Float>();
		Float[] analyze01={0.06f,0.08f,0.1f,0.12f,0.15f,0.2f,0.25f};
		Integer[] analyze02={30,90,182,272,365,547,730};
		Integer[] analyze03={7,30,90,182,272,365};
		float amount=0.0f;
		for(int i=0;i<xVals.size();i++){
			counts.add(0.0f);
		}
		if(allRecords.moveToFirst()){
			while (allRecords.moveToNext()) {
				RecordModel record=new RecordModel(allRecords);
				int time=parseDay(record.getTimeEnd());
				//如果没有到期
				if(time>this.days){
					switch (type) {
					case 0:{
						for(int i=0;i<xVals.size();i++){
							String platformString=record.getPlatform();
							if(platformString.equals(xVals.get(i))){
								counts.set(i, counts.get(i)+record.getMoney());
								amount+=record.getMoney();
							}
						}
						break;
					}
					case 1:{
						Float profit=record.getEarningMax();
						boolean added=false;
						for(int i=0;i<analyze01.length&&added==false;i++){
							if(profit<analyze01[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					case 2:{
						int duration=parseDay(record.getTimeEnd())-parseDay(record.getTimeBegin());
						boolean added=false;
						for(int i=0;i<analyze02.length&&added==false;i++){
							if(duration<analyze02[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					case 3:{
						int left=parseDay(record.getTimeEnd())-days;
						boolean added=false;
						for(int i=0;i<analyze03.length&&added==false;i++){
							if(left<analyze03[i]){
								added=true;
								counts.set(i, counts.get(i)+1.0f);
								amount+=1.0f;
							}
						}
						if (added==false) {
							counts.set(xVals.size()-1, counts.get(xVals.size()-1)+1.0f);
							amount+=1.0f;
						}						
					}
					default:
						break;
				}
				}
									
			}
		}
		for(int i = 0; i < xVals.size(); i++) {
			float result=100f*counts.get(i)/amount;
            entries1.add(new Entry(result, i));
        }
		return entries1;
	}

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
}
