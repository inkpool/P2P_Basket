package com.p2pnote.listitem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.p2pnote.db.model.Invest;
import com.p2pnote.db.model.UserChannelCurrAmount;
import com.p2pnote.ui.ChannelListActivity;
import com.p2pnote.ui.ChannelListActivity;
import com.p2pnote.ui.R;
import com.p2pnote.utility.Function;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChannelListAdapter extends BaseAdapter {

	ChannelListActivity investView;
	ArrayList<Object> trans;
	LayoutInflater inflater;
	private Context mContext;
	public List<UserChannelCurrAmount> bankList;

	/*
	 * public ChannelListAdapter(ChannelListActivity investView,
	 * ArrayList<Object> trans) { this.investView = investView; this.trans =
	 * trans; this.inflater = LayoutInflater.from(investView); }
	 */

	public ChannelListAdapter(Context context,
			List<UserChannelCurrAmount> appList) {
		super();

		mContext = context;
		bankList = appList;
	}

	public int getCount() {
		return bankList.size();
	}

	public Object getItem(int position) {
		return bankList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.channel_list_item, null);

		RelativeLayout list = (RelativeLayout) convertView
				.findViewById(R.id.list);
		TextView bankName = (TextView) convertView
				.findViewById(R.id.channel_name);
		ImageView line = (ImageView) convertView.findViewById(R.id.line);
		TextView useableAmount = (TextView) convertView
				.findViewById(R.id.useable_num);
		TextView holding_amount = (TextView) convertView
				.findViewById(R.id.holding_amount);
		TextView holding_interest = (TextView) convertView
				.findViewById(R.id.holding_interest);

		UserChannelCurrAmount wb = bankList.get(position);

		if (wb != null) {
			convertView.setTag(wb.getId());
			bankName.setText(wb.getChannelName());
			useableAmount.setText("可用余额: "
					+ String.valueOf(wb.getUseableAmount()));
			holding_amount.setText("待取本金: "
					+ String.valueOf(wb.getHoldingAmount()));
			holding_interest.setText("待取利息: "
					+ String.valueOf(wb.getHoldingInterest()));
			int a = wb.getId() % 6;
			switch (a) {

			case 0:

				list.setBackgroundResource(R.drawable.card_list_bg1_normal);// R.drawable.
				line.setBackgroundResource(R.drawable.card_list_line1);
				break;
			case 1:
				list.setBackgroundResource(R.drawable.card_list_bg2_normal);
				line.setBackgroundResource(R.drawable.card_list_line2);
				break;
			case 2:
				list.setBackgroundResource(R.drawable.card_list_bg3_normal);
				line.setBackgroundResource(R.drawable.card_list_line3);
				break;
			case 3:
				list.setBackgroundResource(R.drawable.card_list_bg4_normal);
				line.setBackgroundResource(R.drawable.card_list_line4);
				break;
			case 4:
				list.setBackgroundResource(R.drawable.card_list_bg5_normal);
				line.setBackgroundResource(R.drawable.card_list_line5);
				break;
			case 5:
				list.setBackgroundResource(R.drawable.card_list_bg6_normal);
				line.setBackgroundResource(R.drawable.card_list_line6);
				break;

			default:
				list.setBackgroundResource(R.drawable.card_list_bg1_normal);
				line.setBackgroundResource(R.drawable.card_list_line1);
				break;

			}
		}

		return convertView;
	}

	/*
	 * @Override public int getCount() { return trans.size(); }
	 * 
	 * @Override public Object getItem(int position) { return
	 * trans.get(position); }
	 * 
	 * @Override public long getItemId(int position) { return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { Object item = trans.get(position); if (item.getClass() ==
	 * Invest.class) { convertView =
	 * inflater.inflate(R.layout.common_expense_lv_item, null); Invest data =
	 * (Invest) item; int icon = 0; String name = data.getP2pChannelName();
	 * 
	 * ((ImageView) convertView.findViewById(R.id.category_icon_iv))
	 * .setBackgroundResource(icon); ((TextView)
	 * convertView.findViewById(R.id.p2p_channel_name_tv)) .setText(name);
	 * 
	 * String cost = String.format("￥%.2f", data.getAmount()); TextView cost_tv
	 * = (TextView) convertView .findViewById(R.id.cost_tv);
	 * cost_tv.setText(cost);
	 * 
	 * int days=Function.nDaysBetweenTwoDate(data.getInvestDate(),
	 * data.getRepaymentEndingDate()); String interest = String.format("+%.2f",
	 * data.getInterestRateMin() data.getAmount() / 100 / 365 * days); TextView
	 * interest_tv = (TextView) convertView .findViewById(R.id.interest_tv);
	 * interest_tv.setText(interest);
	 * 
	 * convertView.setTag(data); } else { convertView = inflater.inflate(
	 * R.layout.widget_separated_listview_title, null); TextView title =
	 * (TextView) convertView .findViewById(R.id.list_header_title);
	 * title.setText(item.toString()); convertView.setTag(null); }
	 * 
	 * return convertView; }
	 */
}
