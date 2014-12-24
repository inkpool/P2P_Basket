/**
 * 
 */
package com.crowley.p2pnote;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.crowley.p2pnote.db.MyDbHelper;
import com.crowley.p2pnote.R;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends Activity  {
	 public static MyDbHelper db = null;
	 private Timer mTimer;  
	 private TimerTask mTimerTask; 
	 private Handler handler;
	 LinearLayout layout;	 
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_activity);
			initialDBData();
			//CommonData.getInstance().load(this);
			
		}
	
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			//activity jump
			Intent i=new Intent(SplashScreenActivity.this,MainActivity.class);
			startActivity(i);
			this.finish();
			return super.onTouchEvent(event);
		}
		

	
	private void initialDBData() {
		// 建立数据库
//		deleteDatabase("mydb");
		db = MyDbHelper.getInstance(this.getApplicationContext());
		Resources res = this.getResources();
		db.open();
		Cursor cursor = db.rawQuery("select * from channel_dim",null);
		if (cursor.moveToNext()) {
			cursor.close();
			//db.close();
			return;
		}
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String date_nowString = sDateFormat.format(new java.util.Date());
		
		// 插入常见p2p平台
		String[] arr = res.getStringArray(R.array.channel_dim);
		for (int i = 0; i < arr.length; i++) {
			Log.i("TEST", arr[i]);
			db.insert("channel_dim", new String[] { "channel_name",
					"gmt_create","gmt_update" }, new String[] { arr[i],date_nowString,date_nowString});

		}
		
		//db.close();
	}

}
