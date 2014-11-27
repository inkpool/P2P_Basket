package com.p2pnote.listitem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.p2pnote.ui.R;
import com.p2pnote.ui.R.string;
import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.MyDbInfo;
import com.p2pnote.db.model.Invest;
import com.p2pnote.ui.DetailPageActivity;
import com.p2pnote.utility.MyDialog;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailPageAsyncTask extends
		AsyncTask<DetailPageActivity, Void, Void> {

	DetailPageActivity investView;
	ArrayList<Object> trans = new ArrayList<Object>();
	
	private MyDbHelper db = null;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private List<Invest> list = new ArrayList<Invest>(); 
    private List<Map<String, String>> splitData=new ArrayList<Map<String, String>>();
    private Invest data = null;
	double invest_amount = 0, interest = 0;
	
	private String date = null;
	private String sql_sum,sql_select;

	@Override
	protected Void doInBackground(DetailPageActivity... params) {
				
		investView = params[0];
		// query database;
		// this is test code
		db = investView.db;
		Cursor cursor;
		
		switch (investView.mode) {
			case DetailPageActivity.mode_today_invest:
				sql_sum=investView.getString(R.string.sql_week_sum);
				sql_select=investView.getString(R.string.sql_week_select);
				break;
			case DetailPageActivity.mode_already_expire: 	
				sql_sum=investView.getString(R.string.sql_already_expire_sum);
				sql_select=investView.getString(R.string.sql_already_expire_select);
				break;
			case DetailPageActivity.mode_will_expire:
				sql_sum=investView.getString(R.string.sql_will_expire_sum);
				sql_select=investView.getString(R.string.sql_will_expire_select);
				break;			
			default:
				sql_sum=null;
				sql_select=investView.getString(R.string.sql_total_select);
		}
		
		if (investView.mode>DetailPageActivity.mode_none)
		{	
			cursor = db.rawQuery(sql_sum,new String[]{investView.start_time,investView.end_time});
			if (cursor.moveToNext()) {
				if(cursor.getInt(0) >0)
					invest_amount = cursor.getDouble(1);
					interest = cursor.getDouble(2);
			}
				
			cursor.close();
		}
		
		if (investView.mode>DetailPageActivity.mode_none)
			cursor = db.rawQuery(sql_select,new String[]{investView.start_time,investView.end_time});
		else
			cursor = db.rawQuery(sql_select,null);
		while (cursor.moveToNext()) {
			data = new Invest(cursor.getLong(cursor.getColumnIndex("invest_id")),
					cursor.getLong(cursor.getColumnIndex("user_id")),
					cursor.getString(cursor.getColumnIndex("invest_date")),
					cursor.getString(cursor.getColumnIndex("repayment_ending_date")),
					cursor.getString(cursor.getColumnIndex("p2p_channel_name")),
					cursor.getString(cursor.getColumnIndex("invest_name")),
					cursor.getDouble(cursor.getColumnIndex("amount")),
					cursor.getDouble(cursor.getColumnIndex("interest_rate_min")),
					cursor.getDouble(cursor.getColumnIndex("interest_rate_max")),
					cursor.getString(cursor.getColumnIndex("repayment_type")),
					cursor.getString(cursor.getColumnIndex("guarantee_type")),
					cursor.getString(cursor.getColumnIndex("gmt_create")),
					cursor.getString(cursor.getColumnIndex("gmt_update")),
					cursor.getString(cursor.getColumnIndex("comment"))
					);
			list.add(data);
		}
		
		/*
		Collections.sort(list, new Comparator<Invest>(){  
			  
            public int compare(Invest o1, Invest o2)  
            {  
                //取出操作时间  
                int ret = 0;  
                try  
                {  
                	ret = df.parse(o2.getGmtUpdate()).compareTo(df.parse(o1.getGmtUpdate()));   
                } catch (Exception e)  
                {                     
                    throw new RuntimeException(e);  
                }  
                return  ret;  
            }  
              
        });  
        */
		ListIterator<Invest> iterator = list.listIterator();
		while(iterator.hasNext()){
			Invest da = iterator.next();
			if(date == null || !date.equals(da.getInvestDate())){
				date = da.getInvestDate();
				trans.add(da.getInvestDate());
			}
			trans.add(da);
		}
		
		cursor.close();
		return null;
	}
	
	private void setData() {  
        // 组织数据源  
        Map<String, String> mp = new HashMap<String, String>();  
        mp.put("itemTitle", "A");  
        splitData.add(mp);
        
        /*
        mp = new HashMap<String, String>();  
        mp.put("itemTitle", "B");  
          
        splitList.add(mp);  

        for (int i = 0; i < 6; i++) {  
            Map<String, String> map = new HashMap<String, String>();  
            map.put("itemTitle", "文章2-" + i);  
            mylist.add(map);  
        } 
        */ 
    }  

	@Override
	protected void onPostExecute(Void result) {
		investView.findViewById(R.id.listview_loading_tv).setVisibility(View.GONE);
		if (trans.size() == 0) 
			investView.findViewById(R.id.lv_empty_iv).setVisibility(View.VISIBLE);
		else
			investView.findViewById(R.id.lv_empty_iv).setVisibility(View.GONE);

		//((TextView)investView.findViewById(R.id.invest_amount_tv)).setText(String.format("+￥%.2f", invest_amount));
		//((TextView)investView.findViewById(R.id.interest_tv)).setText(String.format("-￥%.2f", interest));
		
		investView.expense_lv.setAdapter(new DetailPageAdapter(investView, (ArrayList<Object>)trans.clone()));
		//investView.expense_lv.setAdapter(new MyAdapter(investView, (ArrayList<Object>)trans.clone(),splitData));
		super.onPostExecute(result);
	}
	
	private String format(Date date){
		String str = "";
		SimpleDateFormat ymd = null;
		ymd = new SimpleDateFormat("yyyy-MM-dd");
		str = ymd.format(date); 
		return str;
	}
}
