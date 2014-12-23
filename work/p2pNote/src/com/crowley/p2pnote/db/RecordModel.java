package com.crowley.p2pnote.db;

import android.database.Cursor;


public class RecordModel {
	
	/*
	 * Ŀǰ��һ����record����¼���е�Ͷ�ʼ�¼
	 * ����ֶηֱ�Ϊ
	 * _id:id��
	 * platform:ƽ̨�� ��½����
	 * type:�����Ͷ����Ŀ �縻Ӯ����
	 * money:Ͷ�ʵı���
	 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
	 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
	 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
	 * timeBegin:��Ϣʱ�� ��ʽΪ2014-12-26���ַ���
	 * timeEnd:����ʱ�� ��ʽΪ2014-12-26���ַ���
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
