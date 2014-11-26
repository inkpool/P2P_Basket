package com.p2pnote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.model.Invest;
import com.p2pnote.listitem.DetailPageAsyncTask;
import com.p2pnote.ui.R;
import com.p2pnote.utility.Function;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPageActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
	
	public static String str_startTime 	= "startTime";
	public static String str_endTime	= "endTime";
	public static String str_title 		= "title";
	public static String str_mode 		= "mode";
	public static final int mode_none 	= 0;
	public final static int mode_already_expire= 1;
	public final static int mode_will_expire 	= 2;
	public final static int mode_today_invest 	= 3;
	private static String datefmt = "yyyy年MM月dd日";
	//private InvestSQLiteOpenHelper helper = null;
	public MyDbHelper db = null;
	
	public TextView title_tv;
	public TextView time_interval_tv;
	public  ListView expense_lv;
	public View empty_tips;
	
	public String start_time, end_time;
	public String title;
	public int mode;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

		loadingFormation();
    }	     
    
	public void loadingFormation() {
		this.db = SplashScreenActivity.db;
		if (db==null)
		{	
			db=MyDbHelper.getInstance(this.getApplicationContext());
			db.open();
		}
		
		title_tv 			= (TextView)findViewById(R.id.title_tv);
		expense_lv			= (ListView)findViewById(R.id.expense_lv);
		
		empty_tips = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.common_lv_empty_tips, null);
		
		findViewById(R.id.pre_btn).setOnClickListener(this);
		findViewById(R.id.next_btn).setOnClickListener(this);
		
		expense_lv.setOnItemClickListener(this);
		expense_lv.setOnItemLongClickListener(this);

		mode= mode_none;
		
		expense_lv.setEmptyView(empty_tips);
		title_tv.setText(title);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 0) {
			refreshTransactions();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		refreshTransactions();
	}

	@Override
	public void onClick(View v) {		
		//setTimeIntervalText();
		refreshTransactions();
	}

	@Override //弹出的删除与编辑  对话框
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		class NavItemLongClickListener implements DialogInterface.OnClickListener {
			DetailPageActivity nav;
			Invest data;

			public NavItemLongClickListener(DetailPageActivity nav, Invest data) {
				this.nav = nav;
				this.data = data;
			}
			
			public void onClick(DialogInterface dialog, int which) {
				if (data != null) {
					if (which == 0) {
						Intent intent=null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date_repayment = null,date_current = null;
						try {
							date_repayment = sdf.parse(data.getRepaymentEndingDate());
							date_current = sdf.parse(Function.getCurrentDate());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//如果已经到期，直接打开到帐确认页面
						if (date_repayment.compareTo(date_current)<=0)
						{	
							intent= new Intent(nav, RecordConfirmActivity.class);
						}	
						else
						{	
							intent= new Intent(nav, RecordMaintainActivity.class);
							intent.putExtra("mode", RecordMaintainActivity.EDIT_MODE);
						}	
						Bundle mBundle = new Bundle();  
				        mBundle.putParcelable("data", data);  
						intent.putExtras(mBundle);
						intent.putExtra(str_mode, mode);
						nav.startActivityForResult(intent, 0);
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(nav);
						builder.setTitle(R.string.delete_title);
						builder.setMessage(R.string.message_error_system);

						builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								nav.deleteTransaction(data);
								Toast.makeText(nav, getString(R.string.message_delete_ok), 0).show();
							}
						});
						
						builder.setNegativeButton(R.string.delete_cancel, null);

						builder.create().show();
					}
				} else {
					Toast.makeText(nav, getString(R.string.message_error_edit), 0).show();
				}
			}
		}
		
		Invest data = (Invest)view.getTag();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(R.array.setting_listview_item_operation, new NavItemLongClickListener(this, data));
		
		builder.create().show();

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Invest data = (Invest)view.getTag();
		if(data != null){
			Intent intent=null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_repayment = null,date_current = null;
			try {
				date_repayment = sdf.parse(data.getRepaymentEndingDate());
				date_current = sdf.parse(Function.getCurrentDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//如果已经到期，直接打开到帐确认页面
			if (date_repayment.compareTo(date_current)<=0)
			{	
				intent= new Intent(this, RecordConfirmActivity.class);
			}	
			else
			{	
				intent= new Intent(this, RecordMaintainActivity.class);
				intent.putExtra("mode", RecordMaintainActivity.EDIT_MODE);
			}	
			intent.putExtra("data", data);
			startActivityForResult(intent, 0);	
		}
	}
	
	private void refreshTransactions() {
		new DetailPageAsyncTask().execute(this);
	}
	
	public void deleteTransaction(Invest data)
	{
		int id = 0;
		String sql=getString(R.string.sql_delete_invest);
		db.execSQL(sql,new String[] {String.valueOf(data.getInvestId())});
		refreshTransactions();
	}
    
}