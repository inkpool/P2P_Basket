package com.p2pnote.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import com.p2pnote.ui.ReportActivity;
import com.p2pnote.ui.R;
import com.p2pnote.ui.R.array;
import com.p2pnote.ui.R.id;
import com.p2pnote.ui.R.layout;
import com.p2pnote.ui.R.string;
import com.p2pnote.chart.DrawHistogram;
import com.p2pnote.chart.DrawPie;
import com.p2pnote.chart.PieChart;
import com.p2pnote.db.MyDbHelper;

public class ReportActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnItemSelectedListener {
	private String sqlString, report_title;
	Spinner spinner_report_type;
	private LinearLayout layoutPie, layoutHistogram;
	public static String str_startTime = "startTime";
	public static String str_endTime = "endTime";
	Date date1, date2;
	final static int INCOME_MODE = 0;
	final static int PAYOUT_MODE = 1;
	private int type = PAYOUT_MODE;// 操作类型0收入、1支出

	final static int WEEK_MODE = 1;
	final static int MONTH_MODE = 2;
	final static int YEAR_MODE = 3;

	private int dateType = 1;// 操作类型 1周，2月，3年

	public long start_time, end_time;
	private RadioGroup trans_type_tab_rg = null;
	private RadioButton rb1 = null;
	private RadioButton rb2 = null;
	private RadioGroup date_tab = null;
	private RadioButton day = null;
	private RadioButton week = null;
	private RadioButton month = null;
	private RadioButton year = null;
	
	private MyDbHelper db=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_page);
		init();
		drawPie();
		drawHistongram();

		// String[] color = {"#99CC00","#FFBB33","#AA66CC"};
		// PieGraph pg = (PieGraph)this.findViewById(R.id.piegraph);

		/*
		 * spinner_report_type=(Spinner)this.findViewById(R.id.spinner_report_type
		 * ); spinner_report_type.setOnItemSelectedListener(new
		 * SpinnerSelectedListener());
		 * report_title=this.getResources().getStringArray
		 * (R.array.report_type)[0]; spinner_report_type.setSelection(0);
		 */
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		drawPie();
		drawHistongram();

	}

	private void drawHistongram() {
		// DrawHistogram
		//helper = new InvestSQLiteOpenHelper(this);
		//SQLiteDatabase db = this.helper.getReadableDatabase();
		sqlString = getString(R.string.sql_invest_date_report);
		View viewHistogram = new DrawHistogram(sqlString,db).execute(ReportActivity.this); // 画条形图
		layoutHistogram.removeAllViews();
		layoutHistogram.setBackgroundColor(Color.TRANSPARENT);
		layoutHistogram.addView(viewHistogram);
	}

	private void drawPie() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		switch (dateType) {
		case WEEK_MODE:
			c = Calendar.getInstance();
			int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (day_of_week == 0)
				day_of_week = 7;
			c.add(Calendar.DATE, -day_of_week + 1);
			date1 = c.getTime();

			c = Calendar.getInstance();
			day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (day_of_week == 0)
				day_of_week = 7;
			c.add(Calendar.DATE, -day_of_week + 7);
			date2 = c.getTime();

			break;

		case MONTH_MODE:
			c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);// 本月第一天
			date1 = c.getTime();
			c = Calendar.getInstance();
			c.add(Calendar.MONTH, 1);// 本月最后一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DAY_OF_MONTH, -1);
			date2 = c.getTime();

			break;

		case YEAR_MODE:
			c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_YEAR, 1);// 本年第一天
			date1 = c.getTime();
			c = Calendar.getInstance();
			c.add(Calendar.YEAR, 1);
			c.set(Calendar.DAY_OF_YEAR, 1);// 本年最后一天
			c.add(Calendar.DAY_OF_YEAR, -1);
			date2 = c.getTime();

			break;
		}

		DrawPie pieChart = new DrawPie(ReportActivity.this);
		int Count = 0;

		sqlString = getString(R.string.sql_channel_report);
		Count = pieChart.DataPrepare(db, sqlString, date1, date2); // 消费图

		if (Count > 0) {
			View viewPie = pieChart.execute(); // 画饼图
			layoutPie.removeAllViews();
			layoutPie.setBackgroundColor(Color.TRANSPARENT);
			layoutPie.addView(viewPie);
		} else {
			layoutPie.removeAllViews();
			layoutPie.setBackgroundColor(Color.TRANSPARENT);
		}

	}

	private void init() {

		layoutHistogram = (LinearLayout) findViewById(R.id.chartlayout);
		layoutPie = (LinearLayout) findViewById(R.id.pielayout);

		date_tab = (RadioGroup) findViewById(R.id.date_tab);
		week = (RadioButton) findViewById(R.id.week);// 周
		month = (RadioButton) findViewById(R.id.month);
		year = (RadioButton) findViewById(R.id.year);

		year.setChecked(true); // 默认 选中 年
		week.setOnCheckedChangeListener(this);
		month.setOnCheckedChangeListener(this);
		year.setOnCheckedChangeListener(this);
		
		db=SplashScreenActivity.db;

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if (week.isChecked())// 收入
		{

			dateType = WEEK_MODE;
		}
		if (month.isChecked())// 收入
		{

			dateType = MONTH_MODE;
		}
		if (year.isChecked())// 收入
		{
			dateType = YEAR_MODE;

		}

		drawPie();

	}

	// public void onBackPressed() {
	//
	// Intent i=new Intent(ReportActivity.this,MainTabActivity.class);//
	// MainActivity MainTabActivity
	// startActivity(i);
	// ReportActivity.this.finish();
	// super.onBackPressed();
	// }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void showChart(String sqlString, int arg2) {
		report_title = this.getResources().getStringArray(R.array.report_type)[arg2];
		Cursor cursor = db.rawQuery(sqlString, new String[] {});
		int length = cursor.getCount();

		if (length > 0) {
			boolean next = cursor.moveToFirst();
			int number = 0;
			double[] values = new double[length];
			String[] titles = new String[length];

			while (next) {
				titles[number] = cursor.getString(0);
				values[number] = cursor.getFloat(2);
				next = cursor.moveToNext();
				number++;
			}

			Intent achartIntent = new PieChart(values, titles, report_title)
					.execute(this);
			startActivity(achartIntent);
		}
	}
	
	/*
	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg2) {
			case 0:
				sqlString = getString(R.string.sql_channel_report);
				break;
			case 1:
				sqlString = getString(R.string.sql_repayment_date_report);
				break;
			case 2:
				sqlString = getString(R.string.sql_interest_report);
				break;
			}

			showChart(sqlString, arg2);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}
	*/
}
