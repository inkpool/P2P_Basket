package com.p2pnote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.actionbarsherlock.internal.view.View_HasStateListenerSupport;
import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.model.Invest;
import com.p2pnote.listitem.DetailPageAsyncTask;
import com.p2pnote.ui.R;
import com.p2pnote.utility.Function;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPageActivity extends Activity implements View.OnClickListener,View.OnTouchListener,AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
	
	public static String STR_MODE 		= "mode";
	public static final int MODE_NONE 	= 0;
	public static final int MODE_TEMPLATE 	= 1;
	public static final int MODE_SEARCH 	= 2;

	public MyDbHelper db = null;
	
	private TextView title_tv;
	public  ListView expense_lv;
	private View empty_tips;
	private EditText edit_invest_month,edit_channel_name;
	private Button btn_search;
	
	public String invest_month, p2p_channel_name;
	public String title;
	public int mode;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

		Intent intent = getIntent();
		mode = intent.getIntExtra(STR_MODE, 0);

		loadingFormation();
    }	     
    
	public void loadingFormation() {
		this.db = SplashScreenActivity.db;
		if (db==null)
		{	
			db=MyDbHelper.getInstance(this.getApplicationContext());
			db.open();
		}
		
		//title_tv 			= (TextView)findViewById(R.id.title_tv);
		expense_lv			= (ListView)findViewById(R.id.expense_lv);
		
		empty_tips = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.common_lv_empty_tips, null);
			
		expense_lv.setOnItemClickListener(this);
		expense_lv.setOnItemLongClickListener(this);
			
		expense_lv.setEmptyView(empty_tips);
		
		edit_invest_month=(EditText) findViewById(R.id.edit_invest_month);
		edit_channel_name=(EditText) findViewById(R.id.edit_channel_name);
		btn_search=(Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		
		if (mode==MODE_TEMPLATE)
		{
			edit_channel_name.setVisibility(View.GONE);
			btn_search.setVisibility(View.GONE);	
			edit_invest_month.setText(R.string.template);
		}
		
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
		switch (v.getId()) {
		case R.id.btn_search:
			try {
				if (!check())
					return;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mode=MODE_SEARCH;
			refreshTransactions();
			break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_invest_month:
			if (edit_invest_month.getText().toString().equals(R.string.input_invest_month))
				edit_invest_month.setText("");
			break;
		case R.id.edit_channel_name:
			if (edit_channel_name.getText().toString().equals(R.string.input_p2p_channel_name))
				edit_channel_name.setText("");
			break;
		}
		return false;
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
							intent.putExtra(STR_MODE, RecordMaintainActivity.EDIT_MODE);
						}	
						Bundle mBundle = new Bundle();  
				        mBundle.putParcelable("data", data);  
						intent.putExtras(mBundle);
						//intent.putExtra(str_mode, mode);
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
				intent.putExtra(STR_MODE, RecordMaintainActivity.EDIT_MODE);
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
	
	public Boolean check() throws ParseException {
		if (TextUtils.isEmpty(edit_channel_name.getText()) && TextUtils.isEmpty(edit_invest_month.getText())) {
			Toast.makeText(this, "过滤条件不能都为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		p2p_channel_name=edit_channel_name.getText().toString()+"%";
		invest_month=edit_invest_month.getText().toString()+"%";
		if (invest_month.length()!=8 && invest_month.lastIndexOf("-")>=0)
		{	
			Toast.makeText(this, "时间格式填写有误", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}
    
}