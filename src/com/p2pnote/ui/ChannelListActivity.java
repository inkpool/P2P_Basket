package com.p2pnote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import com.p2pnote.ui.R;
import com.p2pnote.utility.Function;
import com.p2pnote.db.MyDbHelper;
import com.p2pnote.db.model.Invest;
import com.p2pnote.db.model.UserChannelCurrAmount;
import com.p2pnote.listitem.ChannelListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

public class ChannelListActivity extends Activity implements View.OnClickListener
{
	private ChannelListAdapter adapater;
	public List<UserChannelCurrAmount> bList=null;       //链表
    public MyDbHelper db = null;
    public ListView listView;  
    int POSTION=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.channel_list);
    
        updateUI();
    } 
    @Override
    public void onResume(){
    	super.onResume();
    	
    	updateUI();
    }
    
    private void updateUI(){
        
        init_list();
        show_list();
        
    }
    
    
    private void  init_list(){
        
        bList = new ArrayList<UserChannelCurrAmount>();//
        
        //{"ID","CARD_NUM","BANK_ID","CARD_TYPE","BILL_DATE","REPAYMENT_TYPE","REPAYMENT_DATE","CREDIT_LIMIT","REMARK"},
        db = SplashScreenActivity.db;
        Cursor cursor = db.rawQuery("select * from user_channel_curr_amount" ,null);
        while (cursor.moveToNext()) {   
                Log.d("user_channel_curr_amount",  "cursor cnt:"+cursor.getCount());
                
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String channel_name=cursor.getString(cursor.getColumnIndex("channel_name"));
                int user_id=cursor.getInt(cursor.getColumnIndex("user_id"));
                int invest_cnt=cursor.getInt(cursor.getColumnIndex("invest_cnt"));
                double invest_amount=cursor.getDouble(cursor.getColumnIndex("invest_amount"));
                double useable_amount=cursor.getDouble(cursor.getColumnIndex("useable_amount"));
                double holding_amount=cursor.getDouble(cursor.getColumnIndex("holding_amount"));
                double holding_interest=cursor.getDouble(cursor.getColumnIndex("holding_interest"));  
                double overdue_amount=cursor.getDouble(cursor.getColumnIndex("overdue_amount"));
				String gmt_create=cursor.getString(cursor.getColumnIndex("gmt_create"));
				String gmt_update=cursor.getString(cursor.getColumnIndex("gmt_update"));
                UserChannelCurrAmount bInfo=new UserChannelCurrAmount(id, channel_name, user_id, invest_cnt, invest_amount, useable_amount, holding_amount, holding_interest, overdue_amount, gmt_create, gmt_update);
                bList.add(bInfo);
        }
        cursor.close();
        
       if(bList.size()>0)
       {
    	   TextView listview_loading_tv=(TextView)findViewById(R.id.listview_loading_tv);
    	   listview_loading_tv.setVisibility(View.GONE);
       }	   

    }
    private void  show_list(){        
       adapater = new ChannelListAdapter(ChannelListActivity.this,bList);  //
       ListView Msglist=(ListView)findViewById(R.id.list_channel);
       Msglist.setAdapter(adapater);       
       Msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               public void onItemClick(AdapterView<?> adapterView, View view, int position,
                       long id) {
                   UserChannelCurrAmount data = bList.get(position);
                   if(null!=data && null!=data.getChannelName() && data.getChannelName().length()>0)
                   {	
                   	   Log.d("channel data write: ",data.toString());
                       Intent intent = new Intent(ChannelListActivity.this, ChannelMaintainActivity.class);
                       intent.putExtra("channel_data", data);
                       startActivityForResult(intent, 0);                                  
                   } 
                }
         });

    }      
    
    /*
    private void delOneCardInfo(int id)  //删除一条record
    {
        try{
        db = MyDbHelper.getInstance(ChannelListActivity.this);
        db.open();
        db.delete("user_channel_curr_amount",   "ID =?", new String[] { String.valueOf(id)});   
        db.close();
        }catch ( Exception e ){
            e.printStackTrace();
           
        }
        
    }
    */
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
