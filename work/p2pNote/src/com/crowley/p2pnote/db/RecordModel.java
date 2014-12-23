package com.crowley.p2pnote.db;

import android.database.Cursor;


public class RecordModel {
	
	/*
	 * 目前就一个表，record，记录所有的投资记录
	 * 表的字段分别为
	 * _id:id号
	 * platform:平台名 如陆金所
	 * type:具体的投资项目 如富赢人生
	 * money:投资的本金
	 * earningMin:浮动收益率下限 如果是固定收益率 该值为0 格式为小数 即15%的话该值为0.15
	 * earningMax:浮动收益率上限 如果是固定收益率 该值为其收益率
	 * method:计息方式 0为到期还本息 1为 按月还本息 2为按月只还息
	 * timeBegin:计息时间 格式为2014-12-26的字符串
	 * timeEnd:到期时间 格式为2014-12-26的字符串
	 * 
	 */
	
	private String platformString;
	private String typeString;
	private Float moneyFloat;
	private Float earningMinFloat;
	private Float earningMaxFloat;
	private Integer methodInteger;
	private String timeBeginString;
	private String timeEndString;
	
	public RecordModel(){
		platformString="";
		typeString="";
		moneyFloat=0.0f;
		earningMinFloat=0.0f;
		earningMaxFloat=0.0f;
		methodInteger=0;
		timeBeginString="";
		timeEndString="";	
	}
	
	public RecordModel(String platform,String type,Float money,Float earningMin,Float earningMax,Integer method,String timeBegin,String timeEnd){
		platformString=platform;
		typeString=type;
		moneyFloat=money;
		earningMinFloat=earningMin;
		earningMaxFloat=earningMax;
		methodInteger=method;
		timeBeginString=timeBegin;
		timeEndString=timeEnd;	
	}
	
	public RecordModel(Cursor cursor){
		platformString=cursor.getString(cursor.getColumnIndex("platform"));
		typeString=cursor.getString(cursor.getColumnIndex("type"));
		moneyFloat=cursor.getFloat(cursor.getColumnIndex("money"));
		earningMinFloat=cursor.getFloat(cursor.getColumnIndex("earningMin"));
		earningMaxFloat=cursor.getFloat(cursor.getColumnIndex("earningMax"));
		methodInteger=cursor.getInt(cursor.getColumnIndex("method"));
		timeBeginString=cursor.getString(cursor.getColumnIndex("timeBegin"));
		timeEndString=cursor.getString(cursor.getColumnIndex("timeEnd"));	
	}
	
	//get method	
	public String getPlatform(){
		return this.platformString;
	}
	
	public String getType(){
		return this.typeString;
	}
	
	public Float getMoney(){
		return this.moneyFloat;
	}
	
	public Float getEarningMin(){
		return this.earningMinFloat;
	}
	
	public Float getEarningMax(){
		return this.earningMaxFloat;
	}
	
	public Integer getMethod(){
		return this.methodInteger;
	}
	
	public String getTimeBegin(){
		return this.timeBeginString;
	}
	
	public String getTimeEnd(){
		return this.timeEndString;
	}
	
	//set method	
	public void setPlatform(String s){
		this.platformString=s;
	}
	
	public void setType(String s){
		this.typeString=s;
	}
	
	public void setMoney(Float f){
		this.moneyFloat=f;
	}
	
	public void setEarningMin(Float f){
		this.earningMinFloat=f;
	}
	
	public void setEarningMax(Float f){
		this.earningMaxFloat=f;
	}
	
	public void setMethod(Integer i){
		this.methodInteger=i;
	}
	
	public void setTimeBegin(String s){
		this.timeBeginString=s;
	}
	
	public void setTimeEnd(String s){
		this.timeEndString=s;
	}
}
