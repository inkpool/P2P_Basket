package com.p2pnote.chart;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.ui.R;
import com.p2pnote.ui.ReportActivity;
import com.p2pnote.ui.SplashScreenActivity;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

public class DrawPie {
	// 画饼图

	private double value[];
	private String partName[];
	private Context mContent;
	private String pieTitle;
	private MyDbHelper db = SplashScreenActivity.db;

	private static int color[][] = {
			{ Color.RED },
			{ Color.RED, Color.YELLOW },
			{ Color.RED, Color.YELLOW, Color.BLUE },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY, Color.GRAY },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY, Color.GRAY, Color.GREEN },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY,
					Color.WHITE },
			{ Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.CYAN,
					Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY,
					Color.WHITE, Color.MAGENTA },

	};// 字段名

	public DrawPie(Context context) {

		mContent = context;
	}

	public int DataPrepare(MyDbHelper db,String sql, Date start, Date end) { // 查询
		Log.d("p2pnote", "pie sql: "+sql);
		Log.d("p2pnote", format(start));
		Log.d("p2pnote", format(end));
		pieTitle = format(start) + "——" + format(end) + "投资统计";
		// db = MyDbHelper.getInstance(mContent);
		// db.open();

		int i = 0; // 收入二级类别查询
		// select sum(AMOUNT) , NAME from TBL_INCOME , TBL_INCOME_SUB_CATEGORY
		// where TBL_INCOME.INCOME_SUB_CATEGORY_ID=TBL_INCOME_SUB_CATEGORY.ID
		// group by INCOME_SUB_CATEGORY_ID
		Cursor cursor = db.rawQuery(sql, new String[] { format(start),
				format(end) });

		partName = new String[cursor.getCount()]; // 字符串数组必须初始化
		value = new double[cursor.getCount()];
		while (cursor.moveToNext()) {
			if (cursor.getString(0) != null) { //
				Log.e("p2pnote", "ge" + cursor.getCount());

				partName[i] = cursor.getString(cursor.getColumnIndex("p2p_channel_name"));
				value[i] = cursor.getDouble(cursor
						.getColumnIndex("amount"));

				Log.e("p2pnote", partName[i] + ":" + value[i]);
			}
			i++;
		}
		cursor.close();
		//db.close();

		return partName.length;

	}

	public GraphicalView execute() {

		int[] colors = color[partName.length - 1];

		DefaultRenderer renderer = buildCategoryRenderer(colors);
		CategorySeries categorySeries = new CategorySeries(pieTitle);

		for (int i = 0; i < partName.length; i++) {
			if (value[i] > 0) {

				categorySeries.add(partName[i], value[i]);
				Log.e("hzm execute ", partName[i] + ":" + value[i]);
			}
		}

		return ChartFactory.getPieChartView(mContent, categorySeries, renderer);
	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();

		renderer.setLabelsTextSize(14);
		renderer.setChartTitle(pieTitle);
		renderer.setChartTitleTextSize(14);
		renderer.setLegendTextSize(14);
		renderer.setLegendHeight(50);
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);

		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.argb(0, 220, 228, 234));//

		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;

	}

	private String format(Date date) {
		String str = "";
		SimpleDateFormat ymd = null;
		ymd = new SimpleDateFormat("yyyy-MM-dd");
		str = ymd.format(date);
		return str;
	}
}