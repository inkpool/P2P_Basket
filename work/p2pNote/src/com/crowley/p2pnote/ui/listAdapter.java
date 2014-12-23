package com.crowley.p2pnote.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import com.crowley.p2pnote.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class listAdapter extends SimpleAdapter{
	
	public static final int[] PLATFORM_NAMES = {
		R.string.company_name01,
		R.string.company_name02,
		R.string.company_name03,
		R.string.company_name04,
		R.string.company_name05,
		R.string.company_name06,
		R.string.company_name07,
		R.string.company_name08,
		R.string.company_name09
		};
	
	public static final int[] COLOR = {
		R.color.company01,
		R.color.company02,
		R.color.company03,
		R.color.company04,
		R.color.company05,
		R.color.company06,
		R.color.company07,
		R.color.company08,
		R.color.company09
		};
	
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItems;
	private String[] from;
	private int[] to;
	private Context context;

	public listAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context=context;
		this.listItems=data;
		this.from=from;
		this.to=to;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View localView = convertView ;
		if(convertView==null){
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.index_listview_item, null);
			localView = super.getView(position, convertView, parent);			
		}else{
			localView = super.getView(position, convertView, parent);
		}
		String nameString = ((TextView)convertView.findViewById(R.id.item_name)).getText().toString();
		String[] strarray=nameString.split("-");
		for(int i=0;i<9;i++){
			if((strarray[0]).equals(this.context.getResources().getString(PLATFORM_NAMES[i]))){
				((TextView)convertView.findViewById(R.id.item_name)).setTextColor(this.context.getResources().getColor(COLOR[i]));				
			}			
		}
		return localView;
	}

}
