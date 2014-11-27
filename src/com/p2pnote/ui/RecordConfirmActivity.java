package com.p2pnote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.p2pnote.ui.R;
import com.p2pnote.ui.R.id;
import com.p2pnote.ui.R.layout;
import com.p2pnote.db.dao.InvestDao;
import com.p2pnote.db.model.Invest;
import com.p2pnote.utility.Function;

import android.R.integer;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
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

public class RecordConfirmActivity extends Activity implements OnClickListener,
		OnTouchListener {
	TextView text_record_title;
	EditText edit_invest_name;
	EditText edit_channel_name;
	EditText edit_interest_rate_min;
	EditText edit_interest_rate_max;
	EditText edit_amount;
	EditText edit_invest_date;
	EditText edit_repayment_ending_date;
	EditText edit_real_interest;
	EditText edit_channel_balance;
	
	private int flag_date = 0;
	private String date_nowString;

	private Calendar calendar = Calendar.getInstance();
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private String lastweek = null;
	public String today;
	Invest data;
	private long investId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_confirm);
		
		Intent intent = getIntent();
		data = (Invest)getIntent().getParcelableExtra("data");
		
		initDate();
		loadingFormation();
	}

	private void initDate() {
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);

		today = DateToStr(year, month, day); // 显示当前的年月日
	}
	
	private String DateToStr(int year,int month,int day) {
		String str_month = String.valueOf(month);
		String str_day = String.valueOf(day);
		if (month < 10)
			str_month = "0" + str_month;

		if (day < 10)
			str_day = "0" + str_day;

		String result = String.valueOf(year) + "-" + str_month + "-" + str_day; // 显示当前的年月日
		return result;
	}

	private void loadingFormation() {		
		text_record_title=(TextView) this.findViewById(R.id.record_title);
		edit_invest_name = (EditText) this.findViewById(R.id.edit_invest_name);
		edit_channel_name = (EditText) this
				.findViewById(R.id.edit_channel_name);
		edit_interest_rate_min = (EditText) this
				.findViewById(R.id.edit_interest_rate_min);
		edit_interest_rate_max = (EditText) this
				.findViewById(R.id.edit_interest_rate_max);
		edit_amount = (EditText) this.findViewById(R.id.edit_amount);
		edit_invest_date = (EditText) this.findViewById(R.id.edit_invest_date);
		edit_repayment_ending_date =(EditText) this.findViewById(R.id.edit_repayment_ending_date);
		edit_real_interest =(EditText) this.findViewById(R.id.edit_real_interest);
		edit_channel_balance =(EditText) this.findViewById(R.id.edit_channel_balance);
		// 给button增加点击事件
		Button btn_save = (Button) this.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);

		Button btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		
		Button btn_edit = (Button) this.findViewById(R.id.btn_edit);
		btn_edit.setOnClickListener(this);
		
		loadData(data);
	}
	
	private void loadData(Invest data) {		
		if (data!=null)
		{
			edit_invest_name.setText(data.getInvestName());
			edit_channel_name.setText(data.getP2pChannelName());
			edit_invest_date.setText(data.getInvestDate());
			edit_repayment_ending_date.setText(data.getRepaymentEndingDate());
			edit_amount.setText(String.valueOf(data.getAmount()));
			edit_interest_rate_min.setText(String.valueOf(data.getInterestRateMin()));
			if (data.getInterestRateMax()>0)
				edit_interest_rate_max.setText(String.valueOf(data.getInterestRateMax()));
			
			investId=data.getInvestId();
			Double real_interest=Function.getInterest(data.getAmount(), data.getInterestRateMin(), data.getInvestDate(), data.getRepaymentEndingDate(), data.getRepaymentType());
			edit_real_interest.setText(String.format("+%.2f",real_interest));
			edit_channel_balance.setText(String.format("+%.2f",data.getAmount()+real_interest));
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
				Toast.makeText(this, "投资记录确认成功", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "投资记录确认失败", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.btn_cancel:
			intent = new Intent(this, MainTabActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_edit:
			intent = new Intent(this, RecordMaintainActivity.class);
			intent.putExtra("mode", RecordMaintainActivity.EDIT_MODE);
			intent.putExtra("data", data);
			startActivityForResult(intent, 0);	
			break;			
		}
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_invest_date:
			/**
			 * 构造函数原型： public DatePickerDialog (Context context,
			 * DatePickerDialog.OnDateSetListener callBack, int year, int
			 * monthOfYear, int dayOfMonth) content组件运行Activity，
			 * DatePickerDialog.OnDateSetListener：选择日期事件
			 * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
			 * 
			 */
			// 创建DatePickerDialog对象
			if (flag_date == 0) {
				DatePickerDialog dpd = new DatePickerDialog(this, Datelistener,
						year, month, day);
				dpd.show();// 显示DatePickerDialog组件
				flag_date = 1;
			}
			break;
		case R.id.edit_repayment_ending_date:
			/**
			 * 构造函数原型： public DatePickerDialog (Context context,
			 * DatePickerDialog.OnDateSetListener callBack, int year, int
			 * monthOfYear, int dayOfMonth) content组件运行Activity，
			 * DatePickerDialog.OnDateSetListener：选择日期事件
			 * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
			 * 
			 */
			// 创建DatePickerDialog对象
			if (flag_date == 0) {
				DatePickerDialog dpd = new DatePickerDialog(this, Datelistener,
						year, month, day);
				dpd.show();// 显示DatePickerDialog组件
				flag_date = 2;
			}
			break;
		}
		return false;
	}

	public Boolean check() throws ParseException {
		if (TextUtils.isEmpty(edit_amount.getText())) {
			Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (TextUtils.isEmpty(edit_interest_rate_min.getText())) {
			Toast.makeText(this, "年化利率不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (TextUtils.isEmpty(edit_invest_date.getText())) {
			Toast.makeText(this, "投资日期不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date invest_date = sDateFormat.parse(edit_invest_date.getText()
				.toString());
		Date repayment_ending_date = sDateFormat
				.parse(edit_repayment_ending_date.getText().toString());
		if (invest_date.after(repayment_ending_date)) {
			Toast.makeText(this, "投资日期不能大于等于结束日期", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public Boolean save() {
		InvestDao pd = new InvestDao(SplashScreenActivity.db);		
		Invest invest = new Invest();
		//invest.setUserId(1L);
		invest.setInvestName(edit_invest_name.getText().toString());
		invest.setP2pChannelName(edit_channel_name.getText().toString());

		Double amount = Double.parseDouble(edit_amount.getText().toString());
		invest.setAmount(amount);

		Double interest_rate_min = Double.parseDouble(edit_interest_rate_min
				.getText().toString());
		invest.setInterestRateMin(interest_rate_min);

		if (!TextUtils.isEmpty(edit_interest_rate_max.getText())) {
			Double interest_rate_max = Double
					.parseDouble(edit_interest_rate_max.getText().toString());
			invest.setInterestRateMax(interest_rate_max);
		}

		String invest_date = edit_invest_date.getText().toString();
		invest.setInvestDate(invest_date);

		String repayment_ending_date = edit_repayment_ending_date.getText()
				.toString();
		invest.setRepaymentEndingDate(repayment_ending_date);
		
		invest.setInvest_status("已结束");
		
		pd.update(invest, investId);
		return true;

	}

	private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
		/**
		 * params：view：该事件关联的组件 params：myyear：当前选择的年 params：monthOfYear：当前选择的月
		 * params：dayOfMonth：当前选择的日
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {

			// 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			year = myyear;
			month = monthOfYear;
			day = dayOfMonth;
			// 更新日期
			switch (flag_date) {
			case 1:
				edit_invest_date.setText(DateToStr(year,(month + 1),day));
				break;
			case 2:
				edit_repayment_ending_date.setText(DateToStr(year,(month + 1),day));
				break;
			}
			flag_date = 0;
		}
	};

}
