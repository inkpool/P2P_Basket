package com.p2pnote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.p2pnote.ui.R;
import com.p2pnote.ui.R.array;
import com.p2pnote.ui.R.id;
import com.p2pnote.ui.R.layout;
import com.p2pnote.db.dao.UserChannelAmountDao;
import com.p2pnote.db.model.UserChannelCurrAmount;

import android.R.integer;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelMaintainActivity extends Activity implements OnClickListener {
	TextView text_record_title;
	EditText edit_channel_name;
	EditText edit_amount;

	private String date_nowString;

	public String today;
	UserChannelCurrAmount data=null;
	private int channelId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_maintain);
		
		Intent intent = getIntent();
		if (intent.hasExtra("channel_data"))
		{	
			data = (UserChannelCurrAmount)intent.getParcelableExtra("channel_data");
			Log.d("channel data read: ",data.toString());
			loadingFormation();
		}	
		else
		{	
			Toast.makeText(this, getString(R.string.error_system_message), 0).show();
			finish();
		}
	}

	private void loadingFormation() {		
		text_record_title=(TextView) this.findViewById(R.id.record_title);
		edit_channel_name = (EditText) this
				.findViewById(R.id.edit_channel_name);
		edit_amount = (EditText) this.findViewById(R.id.edit_amount);
		
		// 给button增加点击事件
		Button btn_save = (Button) this.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		
		Button btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
				
		loadData(data);
	}
	
	private void loadData(UserChannelCurrAmount data) {		
		if (data!=null)
		{
			edit_channel_name.setText(data.getChannelName());
			edit_channel_name.setFocusable(false);
			edit_amount.setText(String.valueOf(data.getUseableAmount()));
			channelId=data.getId();
		}	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_save:
			try {
				if (!check())
					return;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (save())
				Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "记录保存失败", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.btn_cancel:
			intent = new Intent(this, MainTabActivity.class);
			startActivity(intent);
			break;
		}
	}

	public Boolean check() throws ParseException {
		if (TextUtils.isEmpty(edit_channel_name.getText())) {
			Toast.makeText(this, "平台名称不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (TextUtils.isEmpty(edit_amount.getText())) {
			Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	public Boolean save() {
		bind();
		UserChannelAmountDao pd = new UserChannelAmountDao(SplashScreenActivity.db);
		
		pd.update(data, channelId);			
		return true;

	}
	
	public void bind(){
		//invest.setUserId(1L);
		data.setChannelName(edit_channel_name.getText().toString());

		Double amount = Double.parseDouble(edit_amount.getText().toString());
		data.setUseableAmount(amount);

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		date_nowString = sDateFormat.format(new java.util.Date());

		data.setGmtUpdate(date_nowString);
	}
}
