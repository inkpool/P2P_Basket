package com.crowley.p2pnote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowley.p2pnote.db.DBOpenHelper;
import com.crowley.p2pnote.functions.ReturnList;
import com.crowley.p2pnote.ui.listAdapter;

import android.R.integer;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class IndexFragment extends Fragment implements OnClickListener{	

	private View view;
	private ReturnList returnList;
	private ListView listView;
	private listAdapter list_adapter;
	private List<Map<String, Object>> dataList;
	
	private LinearLayout tab_button01;
	private LinearLayout tab_button02;
	
	private TextView tab_button01_number;
	private TextView tab_button02_number;
	
	private TextView timeTextView;
	private TextView index_info_basic01_number;
	private TextView index_info_basic01_float;
	private TextView index_info_basic02_number;
	private TextView index_info_basic02_float;
	private TextView index_info_basic03_number;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.index_fragment, container, false);
		//TextView text = (TextView) view.findViewById(R.id.test);
		
		returnList = new ReturnList(this.getActivity());
        dataList=new ArrayList<Map<String,Object>>();
        listView=(ListView) view.findViewById(R.id.list_view);
        getData(1);
        list_adapter=new listAdapter(this.getActivity(), dataList, R.layout.index_listview_item, new String[]{"time","item_icon","item_name","item_money","item_profit"}, new int[]{R.id.time,R.id.item_icon,R.id.item_name,R.id.item_money,R.id.item_profit});
        listView.setAdapter(list_adapter);
        
        tab_button01 = (LinearLayout) view.findViewById(R.id.tab_button01);
        tab_button02 = (LinearLayout) view.findViewById(R.id.tab_button02);
        tab_button01_number = (TextView) view.findViewById(R.id.tab_button01_number);
        tab_button02_number = (TextView) view.findViewById(R.id.tab_button02_number);
        tab_button01_number.setText(String.valueOf(returnList.indexCount(0)));
        tab_button02_number.setText(String.valueOf(returnList.indexCount(1)));
        
        timeTextView = (TextView) view.findViewById(R.id.index_info_time);
        index_info_basic01_number = (TextView) view.findViewById(R.id.index_info_basic01_number);
    	index_info_basic01_float = (TextView) view.findViewById(R.id.index_info_basic01_float);
    	index_info_basic02_number = (TextView) view.findViewById(R.id.index_info_basic02_number);
    	index_info_basic02_float = (TextView) view.findViewById(R.id.index_info_basic02_float);
    	index_info_basic03_number = (TextView) view.findViewById(R.id.index_info_basic03_number);
    	
        timeTextView.setText(returnList.getTime());
        index_info_basic01_number.setText(returnList.getBaseInfo01Number01());
    	index_info_basic01_float.setText(returnList.getBaseInfo01Number02());
    	index_info_basic02_number.setText(returnList.getBaseInfo02Number01());
    	index_info_basic02_float.setText(returnList.getBaseInfo02Number02());
    	index_info_basic03_number.setText(returnList.getBaseInfo03());
		
        tab_button01.setOnClickListener(this);
        tab_button02.setOnClickListener(this);
        
        return view;
	}

	//private List<Map<String, Object>> getData(int index){
	//0代表已经过期，1代表即将过期
	private void getData(int type){
		dataList.clear();
		List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
		temp=returnList.indexList(type);
		for(int i=0;i<temp.size();i++){
			dataList.add(temp.get(i));
		}
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())  
        {  
        case R.id.tab_button01:  
            tab_button01.setBackgroundColor(getResources().getColor(R.color.white));  
            tab_button02.setBackgroundColor(getResources().getColor(R.color.tab_bg));
            getData(0);
            list_adapter.notifyDataSetChanged();
            break;  
        case R.id.tab_button02:  
        	tab_button01.setBackgroundColor(getResources().getColor(R.color.tab_bg));  
            tab_button02.setBackgroundColor(getResources().getColor(R.color.white));
            getData(1);
            list_adapter.notifyDataSetChanged();
            break;   
        default:  
            break;  
        }
		
	}
}
