package com.p2pnote.listitem;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.p2pnote.db.model.Invest;
import com.p2pnote.ui.DetailPageActivity;
import com.p2pnote.ui.R;
import com.p2pnote.utility.Function;

import android.R.integer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPageAdapter extends BaseAdapter {

	DetailPageActivity investView;
	ArrayList<Object> trans;
	LayoutInflater inflater;

	public DetailPageAdapter(DetailPageActivity investView,
			ArrayList<Object> trans) {
		this.investView = investView;
		this.trans = trans;
		this.inflater = LayoutInflater.from(investView);
	}

	@Override
	public int getCount() {
		return trans.size();
	}

	@Override
	public Object getItem(int position) {
		return trans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object item = trans.get(position);
		if (item.getClass() == Invest.class) {
			convertView = inflater.inflate(R.layout.common_expense_lv_item,
					null);
			Invest data = (Invest) item;
			int icon = 0;
			String name = data.getP2pChannelName();

			((ImageView) convertView.findViewById(R.id.category_icon_iv))
					.setBackgroundResource(icon);
			((TextView) convertView.findViewById(R.id.p2p_channel_name_tv))
					.setText(name);

			String cost = String.format("￥%.2f", data.getAmount());
			TextView cost_tv = (TextView) convertView
					.findViewById(R.id.cost_tv);
			cost_tv.setText(cost);
			
			double interest_num=Function.getInterest(data.getAmount(), data.getInterestRateMin(), data.getInvestDate(), data.getRepaymentEndingDate(), data.getRepaymentType());
			String interest = String.format("+%.2f", interest_num);
			TextView interest_tv = (TextView) convertView
					.findViewById(R.id.interest_tv);
			interest_tv.setText(interest);

			convertView.setTag(data);
		} else {
			convertView = inflater.inflate(
					R.layout.widget_separated_listview_title, null);
			TextView title = (TextView) convertView
					.findViewById(R.id.list_header_title);
			title.setText(item.toString());
			convertView.setTag(null);
		}

		return convertView;
	}
}
