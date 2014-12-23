package com.crowley.p2pnote.functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crowley.p2pnote.R;
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
}
