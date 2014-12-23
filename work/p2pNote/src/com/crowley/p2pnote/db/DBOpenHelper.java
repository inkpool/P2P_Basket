package com.crowley.p2pnote.db;


import com.crowley.p2pnote.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
	

	public static final int[] PLATFORM_NAMES = {
		R.string.company_name01,
		R.string.company_name02,
		R.string.company_name03,
		R.string.company_name04,
		R.string.company_name05,
		R.string.company_name06,
		R.string.company_name07,
		R.string.company_name08,
		R.string.company_name09
		};
	
	public static final int[] PLATFORM_ICONS = {
		R.drawable.company_icon01,
		R.drawable.company_icon02,
		R.drawable.company_icon03,
		R.drawable.company_icon04,
		R.drawable.company_icon05,
		R.drawable.company_icon06,
		R.drawable.company_icon07,
		R.drawable.company_icon08,
		R.drawable.company_icon09
		};	
	
	public static final int[] ANALYZE_TITLE = {
		R.string.analyze_piechart01,
		R.string.analyze_piechart02,
		R.string.analyze_piechart03,
		R.string.analyze_piechart04,
		R.string.analyze_piechart05
		};
	
	public static final int[] ANALYZE_TITLE_SMALL = {
		R.string.analyze_piechart01_small,
		R.string.analyze_piechart02_small,
		R.string.analyze_piechart03_small,
		R.string.analyze_piechart04_small,
		R.string.analyze_piechart05_small
		};

	public DBOpenHelper(Context context, String name) {
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * Ŀǰ��һ����record����¼���е�Ͷ�ʼ�¼
		 * ����ֶηֱ�Ϊ
		 * _id:id��
		 * plateform:ƽ̨�� ��½����
		 * type:�����Ͷ����Ŀ �縻Ӯ����
		 * money:Ͷ�ʵı���
		 * earningMin:�������������� ����ǹ̶������� ��ֵΪ0 ��ʽΪС�� ��15%�Ļ���ֵΪ0.15
		 * earningMax:�������������� ����ǹ̶������� ��ֵΪ��������
		 * method:��Ϣ��ʽ 0Ϊ���ڻ���Ϣ 1Ϊ ���»���Ϣ 2Ϊ����ֻ��Ϣ
		 * timeBegin:��Ϣʱ�� ��ʽΪ2014-12-26���ַ���
		 * timeEnd:����ʱ�� ��ʽΪ2014-12-26���ַ���	 
		 */
		db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null)");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public Cursor returALLRecords(SQLiteDatabase db){
		return db.rawQuery("select * from record", null);
	}
	
	
	
	/*
	DBOpenHelper helper = new DBOpenHelper(NewItemActivity.this, "record.db");
	SQLiteDatabase db = helper.getWritableDatabase();
	Cursor cursor = db.rawQuery("select * from record", null);
	if(cursor!=null){
		while (cursor.moveToNext()) {
			Log.i("m_info", "_id:"+cursor.getInt(cursor.getColumnIndex("_id")));
			Log.i("m_info", "platform:"+cursor.getString(cursor.getColumnIndex("platform")));
			Log.i("m_info", "type:"+cursor.getString(cursor.getColumnIndex("type")));
			Log.i("m_info", "money:"+cursor.getFloat(cursor.getColumnIndex("money")));
			Log.i("m_info", "earningMin:"+cursor.getFloat(cursor.getColumnIndex("earningMin")));
			Log.i("m_info", "earningMax:"+cursor.getFloat(cursor.getColumnIndex("earningMax")));
			Log.i("m_info", "method:"+cursor.getInt(cursor.getColumnIndex("method")));
			Log.i("m_info", "timeBegin:"+cursor.getString(cursor.getColumnIndex("timeBegin")));
			Log.i("m_info", "timeEnd:"+cursor.getString(cursor.getColumnIndex("timeEnd")));
			Log.i("m_info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		cursor.close();
	}
	db.close();
	*/
	
	/*SQLiteDatabase db = openOrCreateDatabase("record.db", MODE_PRIVATE, null);
	db.execSQL("create table if not exists record (_id integer primary key autoincrement,platform text not null,type text not null,money real not null,earningMin real not null,earningMax real not null,method integer not null,timeBegin text not null,timeEnd text not null)");
	//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('½����','��Ӯ����',10000.00,0.05,0.08,0,'2014-12-16','2015-12-16')");
	//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('½����','��Ӯ����',10000.00,0.05,0.08,0,'2014-12-17','2015-12-17')");
	//db.execSQL("insert into record(platform,type,money,earningMin,earningMax,method,timeBegin,timeEnd) values('½����','��Ӯ����',10000.00,0.05,0.08,0,'2014-12-18','2015-12-18')");
	ContentValues values = new ContentValues();
	values.put("platform", "½����");
	values.put("type", "��Ӯ����");
	values.put("money", 10000.00);
	values.put("earningMin", 0.01);
	values.put("earningMax", 0.09);
	values.put("method", 0);
	values.put("timeBegin", "2014-12-16");
	values.put("timeEnd", "2015-12-16");
	long rowId = db.insert(TABLENAME, null, values);
	values.clear();
	values.put("type", "��Ӯ����123");
	db.update(TABLENAME, values, "_id>?", new String[]{"3"});
	//db.delete(TABLENAME, "_id>?", new String[]{"4"});
	Cursor cursor = db.rawQuery("select * from record", null);
	
	if(cursor!=null){
		while (cursor.moveToNext()) {
			Log.i("m_info", "_id:"+cursor.getInt(cursor.getColumnIndex("_id")));
			Log.i("m_info", "platform:"+cursor.getString(cursor.getColumnIndex("platform")));
			Log.i("m_info", "type:"+cursor.getString(cursor.getColumnIndex("type")));
			Log.i("m_info", "money:"+cursor.getFloat(cursor.getColumnIndex("money")));
			Log.i("m_info", "earningMin:"+cursor.getFloat(cursor.getColumnIndex("earningMin")));
			Log.i("m_info", "earningMax:"+cursor.getFloat(cursor.getColumnIndex("earningMax")));
			Log.i("m_info", "method:"+cursor.getInt(cursor.getColumnIndex("method")));
			Log.i("m_info", "timeBegin:"+cursor.getString(cursor.getColumnIndex("timeBegin")));
			Log.i("m_info", "timeEnd:"+cursor.getString(cursor.getColumnIndex("timeEnd")));
			Log.i("m_info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		cursor.close();
	}
	db.close();*/

}
